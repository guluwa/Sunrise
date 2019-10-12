package com.guluwa.sunrise

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Date

object Utils {

    fun strToDate(strDate: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val pos = ParsePosition(0)
        return formatter.parse(strDate, pos)
    }

    fun getStringDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(date)
    }
}
