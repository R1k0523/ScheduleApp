package ru.boringowl.scheduleapp.presentation.repository.network.response


class FullScheduleModel(
    var schedulesId: Long? = null,
    var schedulesName: String? = null,
    var oddWeekLessons: Week? = null,
    var evenWeekLessons: Week? = null
)