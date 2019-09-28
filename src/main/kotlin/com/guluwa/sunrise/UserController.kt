package com.guluwa.sunrise

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PathVariable
import sun.text.normalizer.UCharacter.getAge
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.DeleteMapping

@RestController
class UserController {

    // 创建线程安全的Map，用作数据存储
    var users: ConcurrentHashMap<Long, UserModel> = ConcurrentHashMap()

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
    fun getUser(@PathVariable id: Long?): UserModel? {
        return users[id]
    }

    /**
     * 根据 id 更新用户信息
     * @param id
     * @param userModel
     * @return
     */
    @PutMapping("/{id}")
    fun putUser(@PathVariable id: Long?, @ModelAttribute userModel: UserModel): UserModel? {
        users[id]?.username = userModel.username
        users[id]?.password = userModel.password
        return users[userModel.id]
    }

    /**
     * 根据 id 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long?): String {
        users.remove(id)
        return "success"
    }
}