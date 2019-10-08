package com.guluwa.sunrise

import javax.persistence.*
import org.hibernate.annotations.GenericGenerator
import javax.persistence.GeneratedValue


@Entity
@Table(name = "user")
data class UserModel(@Column(nullable = true, unique = true) var nickName: String = "",
                     @Column(nullable = false) var age: Int = 0) {

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36, unique = true)
    var id: String = ""
}