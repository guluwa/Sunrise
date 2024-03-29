package com.guluwa.sunrise

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession

@SpringBootApplication
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)
@MapperScan("com.guluwa.sunrise.mapper")
class SunriseApplication

fun main(args: Array<String>) {
    runApplication<SunriseApplication>(*args)
}