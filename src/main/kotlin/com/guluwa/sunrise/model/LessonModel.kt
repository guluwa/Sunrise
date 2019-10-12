package com.guluwa.sunrise.model

import lombok.Data
import org.hibernate.annotations.GenericGenerator

import javax.persistence.*
import java.util.Date

@Entity
@Table(name = "lesson")
class LessonModel {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    var id: String = ""

    var name: String = ""

    var startDate: Date = Date()

    var address: String = ""

    var userId: String = ""
}
