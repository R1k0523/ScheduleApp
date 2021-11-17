package ru.boringowl.scheduleapp.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.boringowl.scheduleapp.R
import ru.boringowl.scheduleapp.domain.model.LessonEntity


class ScheduleAdapter(
        var lessons: ArrayList<LessonEntity>
) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var subject: TextView = view.findViewById(R.id.subject)
        var lessonType: TextView = view.findViewById(R.id.lesson_type)
        var teacher: TextView = view.findViewById(R.id.teacher)
        var classroom: TextView = view.findViewById(R.id.classroom)
        var time: TextView = view.findViewById(R.id.time)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_lesson, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.subject.text = lessons[position].subject!!.subjectName
        viewHolder.lessonType.text = lessons[position].lessonType
        viewHolder.teacher.text = lessons[position].tutor!!.fullName
        viewHolder.classroom.text = lessons[position].classroom!!.classroomName
        viewHolder.time.text = lessons[position].interval!!.getTextTime()

        if (viewHolder.teacher.text == "") {
            viewHolder.teacher.text = "Преподаватель не указан"
        }
        if (viewHolder.classroom.text == "") {
            viewHolder.classroom.text = " — "
        }
        if (viewHolder.lessonType.text == "") {
            viewHolder.lessonType.text = "—"
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = lessons.size

}

