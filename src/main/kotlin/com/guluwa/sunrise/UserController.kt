package com.guluwa.sunrise

import java.util.concurrent.ConcurrentHashMap
import sun.text.normalizer.UCharacter.getAge
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.*


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
    fun user(@RequestBody user: UserModel): UserModel {
        return userRepository.save(user)
    }

    @DeleteMapping("/user")
    fun deleteUserById(id: String): String {
        userRepository.deleteById(id)
        return "success"
    }
}