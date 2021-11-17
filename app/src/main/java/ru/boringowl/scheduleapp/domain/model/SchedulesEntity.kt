package ru.boringowl.scheduleapp.domain.model





class SchedulesEntity {



    var schedulesId: Long? = null

    

    var schedulesName: String? = null


    var user: UsersEntity? = null


    var lessons: List<LessonEntity>? = null

    fun toModel():ScheduleModel = ScheduleModel(schedulesId, schedulesName, lessons!!.size)
}

class ScheduleModel(
    var schedulesId: Long? = null,
    var schedulesName: String? = null,
    var lessonsCount: Int = 0

)
