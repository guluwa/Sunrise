package com.guluwa.sunrise.service.impl

import com.guluwa.sunrise.SunriseApplication
import com.guluwa.sunrise.Utils
import com.guluwa.sunrise.dto.LessonDTO
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import java.util.HashMap
import com.guluwa.sunrise.service.UserService
import lombok.extern.slf4j.Slf4j
import org.apache.commons.logging.LogFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import com.oracle.util.Checksums.update
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import com.querydsl.jpa.JPAExpressions.selectFrom
import org.apache.tomcat.jni.Buffer.address
import com.guluwa.sunrise.dto.UserDTO
import com.guluwa.sunrise.dto.UserResultDTO
import com.guluwa.sunrise.model.*
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringTemplate
import java.util.stream.Collectors


@Service
@Slf4j
class UserServiceImpl : UserService {

    val log = LogFactory.getLog(SunriseApplication::class.java)!!

    @CachePut(value = ["user"], key = "#user.id")
    override fun save(user: User): User {
        USER_MAP[user.id] = user
        log.info("进入 save 方法，当前存储对象：$user")
        return user
    }

    @Cacheable(value = ["user"], key = "#id")
    override operator fun get(id: Long): User? {
        log.info("进入 get 方法，当前获取对象：" + USER_MAP[id])
        return USER_MAP[id]
    }

    @CacheEvict(value = ["user"], key = "#id")
    override fun delete(id: Long) {
        USER_MAP.remove(id)
        log.info("进入 delete 方法，删除成功")
    }

    companion object {

        private val USER_MAP = HashMap<Long, User>()

        init {
            USER_MAP[1L] = User(1L, "guluwa1", 18)
            USER_MAP[2L] = User(2L, "guluwa2", 19)
            USER_MAP[3L] = User(3L, "guluwa3", 20)
        }
    }

    //=============================================================================================================

    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    override fun update(id: String, nickName: String): Long? {
        val userModel = QPeopleModel.peopleModel
        // 更新
        return queryFactory.update(userModel).set(userModel.nickName, nickName).where(userModel.id.eq(id)).execute()
    }

    override fun delete(id: String): Long? {
        val userModel = QPeopleModel.peopleModel
        // 删除
        return queryFactory.delete(userModel).where(userModel.id.eq(id)).execute()
    }

    override fun selectAllNameList(): List<String> {
        val userModel = QPeopleModel.peopleModel
        // 查询字段
        return queryFactory.select(userModel.nickName).from(userModel).fetch()
    }

    override fun selectAllUserModelList(): List<PeopleModel> {
        val userModel = QPeopleModel.peopleModel
        // 查询实体
        return queryFactory.selectFrom(userModel).fetch()
    }

    override fun selectAllUserDTOList(): List<UserResultDTO> {
        val userModel = QPeopleModel.peopleModel
        val lessonModel = QLessonModel.lessonModel
        // 连表查询实体并将结果封装至DTO
        return queryFactory
                .select(
                        Projections.bean(UserDTO::class.java, userModel.nickName, userModel.age, lessonModel.startDate,
                                lessonModel.address, lessonModel.name)
                )
                .from(userModel)
                .leftJoin(lessonModel)
                .on(userModel.id.eq(lessonModel.userId))
                .fetch()
                .stream()
                .map { res ->
                    val dto = UserResultDTO()
                    dto.nickName = res.nickName
                    dto.age = res.age
                    dto.startDate = Utils.getStringDate(res.startDate)
                    dto.address = res.address
                    dto.name = res.name
                    dto
                }.collect(Collectors.toList<UserResultDTO>())
    }

    override fun selectDistinctNameList(): List<String> {
        val userModel = QPeopleModel.peopleModel
        // 去重查询
        return queryFactory.selectDistinct(userModel.nickName).from(userModel).fetch()
    }

    override fun selectFirstUser(): PeopleModel {
        val userModel = QPeopleModel.peopleModel
        // 查询首个实体
        return queryFactory.selectFrom(userModel).fetchFirst()
    }

    override fun selectUser(id: String): PeopleModel? {
        val userModel = QPeopleModel.peopleModel
        // 查询单个实体，如果结果有多个，会抛`NonUniqueResultException`。
        return queryFactory.selectFrom(userModel).where(userModel.id.eq(id)).fetchOne()
    }

    override fun mysqlFuncDemo(id: String, nickName: String, age: Int): String? {

        val userModel = QPeopleModel.peopleModel

        // Mysql 聚合函数示例

        // 聚合函数-avg()
        val averageAge = queryFactory.select(userModel.age.avg()).from(userModel).fetchOne()
        println("=========== $averageAge")

        // 聚合函数-sum()
        val sumAge = queryFactory.select(userModel.age.sum()).from(userModel).fetchOne()
        println("=========== $sumAge")

        // 聚合函数-concat()
        val concat = queryFactory.select(userModel.nickName.concat(nickName)).from(userModel).where(userModel.id.eq(id)).fetchOne()
        println("=========== $concat")

        // 聚合函数-contains()
        val contains = queryFactory.select(userModel.nickName.contains(nickName)).from(userModel).where(userModel.id.eq(id)).fetchOne()
        println("=========== $contains")

        // 聚合函数-DATE_FORMAT()
        val date = queryFactory.select(Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d')", userModel.createDate)).from(userModel).where(userModel.id.eq(id)).fetchOne()
        println("=========== $date")

        return null
    }
}
