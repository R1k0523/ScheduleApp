package ru.boringowl.scheduleapp.domain.model


class ClassroomEntity {
    var classroomId: Long? = null

    var classroomName: String? = null

    var classroomSize: Int? = null

    var equips: List<EquipmentEntity>? = null
}

