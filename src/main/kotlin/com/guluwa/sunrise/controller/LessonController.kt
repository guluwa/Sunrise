package com.guluwa.sunrise.controller

import com.guluwa.sunrise.Utils
import com.guluwa.sunrise.dto.LessonDTO
import com.guluwa.sunrise.service.LessonService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@Slf4j
@RestController
class LessonController {

    @Autowired
    lateinit var lessonService: LessonService

    @GetMapping("/lessonList")
    fun lessonList(@RequestParam(required = true) name: String,
                   @RequestParam(required = true) startDate: String,
                   @RequestParam(required = true) address: String,
                   @RequestParam(required = true) userId: String): List<LessonDTO> {
        return lessonService.findLessonList(name, Utils.strToDate(startDate), address, userId)
                .stream().map { res ->
                    val dto = LessonDTO()
                    dto.id = res.id
                    dto.name = res.name
                    dto.startDate = Utils.getStringDate(res.startDate)
                    dto.address = res.address
                    dto.userId = res.userId
                    return@map dto
                }.collect(Collectors.toList<LessonDTO>())
    }

    @GetMapping("/lessonDynaList")
    fun lessonDynaList(@RequestParam(required = false, defaultValue = "") name: String,
                       @RequestParam(required = false, defaultValue = "") startDate: String,
                       @RequestParam(required = false, defaultValue = "") address: String,
                       @RequestParam(required = false, defaultValue = "") userId: String): List<LessonDTO> {
        return lessonService.findLessonDynaList(name, Utils.strToDate(startDate), address, userId)
                .stream().map { res ->
                    val dto = LessonDTO()
                    dto.id = res.id
                    dto.name = res.name
                    dto.startDate = Utils.getStringDate(res.startDate)
                    dto.address = res.address
                    dto.userId = res.userId
                    return@map dto
                }.collect(Collectors.toList<LessonDTO>())
    }

    @GetMapping("/lessonSubQueryList")
    fun lessonSubQueryList(@RequestParam(required = true) name: String,
                           @RequestParam(required = true) address: String): List<LessonDTO> {
        return lessonService.findLessonSubQueryList(name, address)
                .stream().map { res ->
                    val dto = LessonDTO()
                    dto.id = res.id
                    dto.name = res.name
                    dto.startDate = Utils.getStringDate(res.startDate)
                    dto.address = res.address
                    dto.userId = res.userId
                    return@map dto
                }.collect(Collectors.toList<LessonDTO>())
    }
}