package ru.boringowl.scheduleapp.presentation.repository.network.response

import ru.boringowl.scheduleapp.domain.model.LessonEntity

class Week(
    var mon: ArrayList<LessonEntity> = arrayListOf(),
    var tue: ArrayList<LessonEntity> = arrayListOf(),
    var wed: ArrayList<LessonEntity> = arrayListOf(),
    var thu: ArrayList<LessonEntity> = arrayListOf(),
    var fri: ArrayList<LessonEntity> = arrayListOf(),
    var sat: ArrayList<LessonEntity> = arrayListOf()
) {
    fun getLessons(dayNum: Int): ArrayList<LessonEntity> {
        return when (dayNum) {
            1 ->  mon
            2 ->  tue
            3 ->  wed
            4 ->  thu
            5 ->  fri
            6 ->  sat
            else -> {
                arrayListOf()
            }
        }
    }
}