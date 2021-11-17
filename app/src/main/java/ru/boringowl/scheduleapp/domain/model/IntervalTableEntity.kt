package ru.boringowl.scheduleapp.domain.model
import java.io.Serializable


class IntervalTableEntity : Serializable {

    var intervalId: IntervalId? = null

    fun getTextTime(): String {
        return when (intervalId!!.lessonNumber) {
            0 -> "09:00\n—\n10:30"
            1 -> "10:40\n—\n12:10"
            3 -> "12:40\n—\n14:10"
            4 -> "14:20\n—\n15:50"
            5 -> "16:20\n—\n17:50"
            6 -> "18:00\n—\n19:30"
            else -> {
                "-"
            }
        }
    }
}


class IntervalId(

    var lessonNumber: Int? = null,


    var weekDay: Int? = null,


    var week: Int? = null,
): Serializable