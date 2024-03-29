package com.guluwa.sunrise.service.impl;

import com.guluwa.sunrise.model.LessonModel;
import com.guluwa.sunrise.model.QLessonModel;
import com.guluwa.sunrise.service.LessonService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    JPAQueryFactory queryFactory;

    @Override
    public List<LessonModel> findLessonList(String name, Date startDate, String address, String userId) throws ParseException {
        QLessonModel lessonModel = QLessonModel.lessonModel;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // 多条件查询示例
        return queryFactory.selectFrom(lessonModel)
                .where(
                        lessonModel.name.like("%" + name + "%")
                        .and(lessonModel.address.contains(address))
                        .and(lessonModel.userId.eq(userId))
                        .and(lessonModel.startDate.between(simpleDateFormat.parse("2018-12-31 00:00:00"), new Date()))
                )
                .fetch();
    }

    @Override
    public List<LessonModel> findLessonDynaList(String name, Date startDate, String address, String userId) throws ParseException {
        QLessonModel lessonModel = QLessonModel.lessonModel;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        // 动态查询示例
        BooleanBuilder builder = new BooleanBuilder();

        if (!StringUtils.isEmpty(name)){
            builder.and(lessonModel.name.like("%" + name + "%"));
        }

        if (startDate != null) {
            builder.and(lessonModel.startDate.between(simpleDateFormat.parse("2018-12-31 00:00:00"), new Date()));
        }

        if (!StringUtils.isEmpty(address)) {
            builder.and(lessonModel.address.contains(address));
        }

        if (!StringUtils.isEmpty(userId)) {
            builder.and(lessonModel.userId.eq(userId));
        }

        return queryFactory.selectFrom(lessonModel).where(builder).fetch();
    }

    @Override
    public List<LessonModel> findLessonSubQueryList(String name, String address) {
        QLessonModel lessonModel = QLessonModel.lessonModel;
        // 子查询示例，并无实际意义
        return queryFactory.selectFrom(lessonModel)
                .where(lessonModel.name.in(
                        JPAExpressions
                                .select(lessonModel.name)
                                .from(lessonModel)
                                .where(lessonModel.address.eq(address))
                ))
                .fetch();
    }
}
