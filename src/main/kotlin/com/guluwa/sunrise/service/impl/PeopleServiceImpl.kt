package com.guluwa.sunrise.service.impl

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.guluwa.sunrise.mapper.PeopleMapper
import com.guluwa.sunrise.model.People
import com.guluwa.sunrise.service.PeopleService
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service

@Service
@Slf4j
class PeopleServiceImpl : ServiceImpl<PeopleMapper, People>(), PeopleService