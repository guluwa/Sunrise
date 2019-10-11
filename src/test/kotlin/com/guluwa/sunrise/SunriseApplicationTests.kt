package com.guluwa.sunrise

import com.guluwa.sunrise.controller.UserController
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.junit.Before
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
@SpringBootTest
class SunriseApplicationTests {

    private var mvc: MockMvc? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        mvc = MockMvcBuilders.standaloneSetup(UserController()).build()
    }

    @Test
    fun contextLoads() {

        var request: RequestBuilder? = null

        // 1、get查一下user列表，应该为空
        request = MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
        mvc?.perform(request)
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andDo { print("-----------------------------------") }
                ?.andReturn()

        // 2、post提交一个user
        request = MockMvcRequestBuilders.post("/")
                .param("id", "1")
                .param("username", "Spring Boot")
                .param("password", "123456")
                .contentType(MediaType.APPLICATION_JSON)
        mvc?.perform(request)
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andDo { print("-----------------------------------") }
                ?.andReturn()

        // 3、get获取user列表，应该有刚才插入的数据
        request = MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
        mvc?.perform(request)
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andDo { print("-----------------------------------") }
                ?.andReturn()

        // 4、put修改id为1的user
        request = MockMvcRequestBuilders.put("/1")
                .param("username", "Spring Boot Test")
                .contentType(MediaType.APPLICATION_JSON)
        mvc?.perform(request)
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andDo { print("-----------------------------------") }
                ?.andReturn()

        // 5、get一个id为1的user
        request = MockMvcRequestBuilders.get("/1")
                .contentType(MediaType.APPLICATION_JSON)
        mvc?.perform(request)
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andDo { print("-----------------------------------") }
                ?.andReturn()

        // 6、del删除id为1的user
        request = MockMvcRequestBuilders.delete("/1")
                .contentType(MediaType.APPLICATION_JSON)
        mvc?.perform(request)
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andDo { print("-----------------------------------") }
                ?.andReturn()

        // 7、get查一下user列表，应该为空

        request = MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
        mvc?.perform(request)
                ?.andExpect(MockMvcResultMatchers.status().isOk)
                ?.andDo(MockMvcResultHandlers.print())
                ?.andDo { print("-----------------------------------") }
                ?.andReturn()
    }

}
