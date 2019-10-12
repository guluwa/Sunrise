package com.guluwa.sunrise.controller

import com.alibaba.druid.util.StringUtils
import com.guluwa.sunrise.SunriseApplication
import com.guluwa.sunrise.Utils
import com.guluwa.sunrise.dto.UserDTO
import com.guluwa.sunrise.dto.UserResultDTO
import com.guluwa.sunrise.model.LessonModel
import com.guluwa.sunrise.model.PeopleModel
import com.guluwa.sunrise.model.User
import com.guluwa.sunrise.model.UserModel
import com.guluwa.sunrise.repository.UserRepository
import com.guluwa.sunrise.service.LessonService
import lombok.extern.slf4j.Slf4j
import org.apache.commons.logging.LogFactory
import java.util.concurrent.ConcurrentHashMap
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.*
import java.io.Serializable
import javax.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import com.guluwa.sunrise.service.UserService
import org.thymeleaf.util.DateUtils
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.transaction.Transactional
import java.util.stream.Collectors
import com.querydsl.core.types.ExpressionUtils.orderBy
import org.springframework.web.bind.annotation.RequestMapping


@Slf4j
@RestController
class UserController {

    // 创建线程安全的Map，用作数据存储
    var users: ConcurrentHashMap<String, UserModel> = ConcurrentHashMap()

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/")
    fun getUserList(): List<UserModel> {
        return users.values.toList()
    }

    /**
     * 创建User
     * @param userModel
     * @return
     */
    @PostMapping("/")
    fun postUser(@ModelAttribute userModel: UserModel): UserModel? {
        users[userModel.id] = userModel
        return users[userModel.id]
    }

    /**
     * {id} 根据 url 中的 id 获取 user 信息
     * url中的id可通过@PathVariable绑定到函数的参数中
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: String?): UserModel? {
        return users[id]
    }

    /**
     * 根据 id 更新用户信息
     * @param id
     * @param userModel
     * @return
     */
    @PutMapping("/{id}")
    fun putUser(@PathVariable id: String?, @ModelAttribute userModel: UserModel): UserModel? {
        users[id]?.nickName = userModel.nickName
        users[id]?.age = userModel.age
        return users[userModel.id]
    }

    /**
     * 根据 id 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: String?): String {
        users.remove(id)
        return "success"
    }

    //=============================================================================================================

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/user")
    fun user(): List<UserModel> {
        return userRepository.findAll(Sort.by("id").descending()).toList()
    }

    @PostMapping("/user")
    fun user(@RequestParam(required = false, defaultValue = "") id: String,
             @RequestParam name: String,
             @RequestParam(required = false, defaultValue = "0") age: Int): Any {
        val model = UserModel(name, age)
        if (id != "") {
            model.id = id
        }
        return userRepository.save(model)
    }

    @DeleteMapping("/user")
    fun deleteUserById(@RequestParam id: String): String {
        userRepository.deleteById(id)
        return "success"
    }

    //=============================================================================================================

    val log = LogFactory.getLog(SunriseApplication::class.java)!!

    @Autowired
    lateinit var mStringRedisTemplate: StringRedisTemplate

    @Autowired
    lateinit var mRedisTemplate: RedisTemplate<String, Serializable>

    @Autowired
    lateinit var userService: UserService


    @GetMapping("/test")
    fun test() {
        mStringRedisTemplate.opsForValue().set("baidu", "www.baidu.com")

        log.info("当前获取对象：" + mStringRedisTemplate.opsForValue().get("baidu"))

        mRedisTemplate.opsForValue().set("guluwa", User(1, "guluwa", 18))

        val user = mRedisTemplate.opsForValue().get("guluwa") as User

        log.info("当前获取对象：$user")
    }

    @GetMapping("/test1")
    fun test1() {
        var user = userService.save(User(4L, "guluwa4", 35))

        log.info("当前 save 对象：$user")

        user = userService[1L]!!

        log.info("当前 get 对象：$user")

        userService.delete(5L)
    }

    @GetMapping("/getBlogUrl")
    fun getSessionId(request: HttpServletRequest): String {
        val url = request.session.getAttribute("url") as String?
        if (StringUtils.isEmpty(url)) {
            request.session.setAttribute("url", "https://www.geekdigging.com/")
        }
        log.info("获取session内容为：" + request.session.getAttribute("url"))
        return request.requestedSessionId
    }

    //=============================================================================================================

    @Transactional
    @PostMapping("/peopleUpdate")
    fun peopleUpdate(@RequestParam id: String, @RequestParam nickName: String): Long {
        return userService.update(id, nickName) ?: -1
    }

    @Transactional
    @PostMapping("/peopleDelete")
    fun peopleDelete(@RequestParam id: String): Long {
        return userService.delete(id) ?: -1
    }

    @GetMapping("/allNameList")
    fun allNameList(): List<String> {
        return userService.selectDistinctNameList()
    }

    @GetMapping("/allUserModelList")
    fun allUserModelList(): List<PeopleModel> {
        return userService.selectAllUserModelList()
    }

    @GetMapping("/allUserDTOList")
    fun allUserDTOList(): List<UserResultDTO> {
        return userService.selectAllUserDTOList()
    }

    @GetMapping("/distinctNameList")
    fun distinctNameList(): List<String> {
        return userService.selectDistinctNameList()
    }

    @GetMapping("/firstUser")
    fun firstUser(): PeopleModel {
        return userService.selectFirstUser()
    }

    @GetMapping("/selectUser")
    fun selectUser(@RequestParam id: String): PeopleModel? {
        return userService.selectUser(id)
    }

    @PostMapping("/mysqlFuncDemo")
    fun selectUser(@RequestParam id: String, @RequestParam nickName: String, @RequestParam age: Int): String? {
        return userService.mysqlFuncDemo(id, nickName, age)
    }
}