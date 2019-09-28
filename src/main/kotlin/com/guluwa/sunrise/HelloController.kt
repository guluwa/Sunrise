package com.guluwa.sunrise

import org.springframework.stereotype.Controller
import java.util.ArrayList
import java.util.HashMap
import javax.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping


@Controller
class HelloController {

    @GetMapping("/hello")
    fun hello(request: HttpServletRequest): String {
        // 构建测试数据
        val map = HashMap<String, Any>()

        val userModel = UserModel()
        userModel.id = 1L
        userModel.username = "Spring Boot"
        userModel.password = "123456"

        map["user"] = userModel

        val list = ArrayList<CourseModel>()

        for (i in 0..1) {
            val courseMode = CourseModel()
            courseMode.id = i.toLong()
            courseMode.name = "Spring Boot：$i"
            courseMode.address = "课程地点：$i"
            list.add(courseMode)
        }

        map["list"] = list

        map["flag"] = true

        request.setAttribute("data", map)
        return "hello"
    }
}