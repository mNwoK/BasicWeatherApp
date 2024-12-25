package com.example.themostbasicweatherapp.ui.util

import java.time.LocalDate
import java.util.Date

fun toRussian(day: String): String {
    when(day) {
        "MONDAY" -> return "Понедельник"
        "TUESDAY" -> return "Вторник"
        "WEDNESDAY" -> return "Среда"
        "THURSDAY" -> return "Четверг"
        "FRIDAY" -> return "Пятница"
        "SATURDAY" -> return "Суббота"
        "SUNDAY" -> return "Воскресенье"
    }
    return "Гойда"
}

fun getNameOfDayOfWeek(date: String): String {
    val dateClass = LocalDate.parse(date)
    return toRussian(dateClass.dayOfWeek.name)
}

