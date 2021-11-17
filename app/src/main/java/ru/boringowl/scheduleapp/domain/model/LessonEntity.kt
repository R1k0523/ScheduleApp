package ru.boringowl.scheduleapp.domain.model


class LessonEntity {
    var lessonId: Long? = null
    var lessonType: String? = null
    var subject: SubjectEntity? = null
    var classroom: ClassroomEntity? = null
    var tutor: TutorEntity? = null
    var group: GroupTableEntity? = null
    var interval: IntervalTableEntity? = null
}

