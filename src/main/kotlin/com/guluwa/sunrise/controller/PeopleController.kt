package com.guluwa.sunrise.controller

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.guluwa.sunrise.mapper.PeopleMapper
import com.guluwa.sunrise.model.People
import com.guluwa.sunrise.service.PeopleService
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class PeopleController {

    @Autowired
    lateinit var peopleMapper: PeopleMapper

    @Autowired
    lateinit var peopleService: PeopleService

    @GetMapping("/findPage")
    fun findPage(): IPage<People> {
        val page = Page<People>(1, 10)

        return peopleMapper.selectPage(page, null)
    }
}