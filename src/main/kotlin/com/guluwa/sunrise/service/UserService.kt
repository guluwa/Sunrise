package com.guluwa.sunrise.service

import com.guluwa.sunrise.dto.UserResultDTO
import com.guluwa.sunrise.model.People
import com.guluwa.sunrise.model.User

interface UserService {

    fun save(user: User): User

    operator fun get(id: Long): User?

    fun delete(id: Long)

    //=============================================================================================================

    fun update(id: String, nickName: String): Long?

    fun delete(id: String): Long?

    fun selectAllNameList(): List<String>

    fun selectAllUserModelList(): List<People>

    fun selectAllUserDTOList(): List<UserResultDTO>

    fun selectDistinctNameList(): List<String>

    fun selectFirstUser(): People

    fun selectUser(id: String): People?

    fun mysqlFuncDemo(id: String, nickName: String, age: Int): String?
}
