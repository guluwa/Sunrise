package com.guluwa.sunrise.repository

import com.guluwa.sunrise.model.UserModel
import org.springframework.data.jpa.repository.JpaRepository


interface UserRepository : JpaRepository<UserModel, String> {

    fun getByIdIs(id: String?): UserModel

    fun findByNickName(nickName: String?): UserModel

    fun countByAge(age: Int?): Int

    fun findByNickNameLike(nickName: String?): List<UserModel>
}