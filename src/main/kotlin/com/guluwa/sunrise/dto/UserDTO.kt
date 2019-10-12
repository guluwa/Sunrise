package com.guluwa.sunrise.dto

import lombok.Data

import java.util.Date

class UserDTO {
    var nickName: String = ""

    var age: Int = 0

    var startDate: Date = Date()

    var address: String = ""

    var name: String = ""
}

class UserResultDTO {
    var nickName: String = ""

    var age: Int = 0

    var startDate: String = ""

    var address: String = ""

    var name: String = ""
}