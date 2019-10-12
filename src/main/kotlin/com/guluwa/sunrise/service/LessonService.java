package com.guluwa.sunrise.service;

import com.guluwa.sunrise.model.LessonModel;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface LessonService {

    List<LessonModel> findLessonList(String name, Date startDate, String address, String userId) throws ParseException;

    List<LessonModel> findLessonDynaList(String name, Date startDate, String address, String userId) throws ParseException;

    List<LessonModel> findLessonSubQueryList(String name, String address);
}
