package ru.boringowl.scheduleapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import ru.boringowl.scheduleapp.R
import ru.boringowl.scheduleapp.domain.model.LessonEntity
import ru.boringowl.scheduleapp.presentation.repository.network.ServerRepository
import ru.boringowl.scheduleapp.presentation.repository.network.response.FullScheduleModel
import java.text.SimpleDateFormat
import java.util.*

class ScheduleViewModel() : ViewModel() {

    val repository: ServerRepository by KoinJavaComponent.inject(ServerRepository::class.java)

    var weekChangedByDay = false

    private val _message= MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _isShowed= MutableLiveData<Boolean>()
    val isShowed: LiveData<Boolean>
        get() = _isShowed

    private val _week = MutableLiveData<FullScheduleModel>()
    val week: LiveData<FullScheduleModel>
        get() = _week


    private val _weekNum = MutableLiveData<Int>().apply {
        this.value = getCurWeek()-2
    }
    val weekNum: LiveData<Int>
        get() = _weekNum


    private val _dayNum = MutableLiveData<Int>().apply {
        this.value = getCurDay()
    }
    val dayNum: LiveData<Int>
        get() = _dayNum

    fun getCurTime(): Long {
        val date = Date()
        return date.time
    }

    private val _group = MutableLiveData<String>().apply {
        this.value = "without"
    }


    fun getSchedule(group: String) {
        viewModelScope.launch {
            try {
                val response = repository.getLessons(group)

                Log.d("tex123t", "eaea" + response.evenWeekLessons!!.mon.size)
                if (response.schedulesName != null)
                    _week.value = response
            } catch (e: Exception) {
                Log.d("tex123t", "eaea" + e.localizedMessage)

            }
        }
    }

    private fun getSemesterStartDate(): Long{
        val startDate = "01.09.2021"
        return  SimpleDateFormat(
            "dd.MM.yyyy", Locale.getDefault()
        ).parse(startDate).time
    }

    private fun getCurWeek(): Int {
        val millsInWeek = 604800000
        return ((getCurTime()-getSemesterStartDate())/millsInWeek+1).toInt()
    }

    private fun getCurDay(): Int {
        val time = getCurTime()
        var day = SimpleDateFormat("u").format(time).toInt()
        if(day==7) day--
        return day
    }

    fun setDay(id: Int){
        val res =
            when(id){
                R.id.mon -> 1
                R.id.tue -> 2
                R.id.wed -> 3
                R.id.thu -> 4
                R.id.fri -> 5
                R.id.sat -> 6
                else -> -1
            }
        if (res != _dayNum.value)
            _dayNum.value = res
    }
    fun getDay(id: Int): Int{
        return when(id){
            R.id.mon -> 1
            R.id.tue -> 2
            R.id.wed -> 3
            R.id.thu -> 4
            R.id.fri -> 5
            R.id.sat -> 6
            else -> 1
        }
    }
    fun getIdByDay(day: Int): Int {
        return when(day){
            1-> R.id.mon
            2-> R.id.tue
            3-> R.id.wed
            4-> R.id.thu
            5-> R.id.fri
            6-> R.id.sat
            else -> R.id.mon
        }
    }

    fun getMonthByWeek(): String? {
        val time = getSemesterStartDate()+_weekNum.value!!.toLong()*604800000
        var month = SimpleDateFormat("M", Locale.forLanguageTag("RU")).format(time)
        val months = arrayOf("Январь","Февраль","Март","Апрель","Май","Июнь","Июль","Август","Сентябрь","Октябрь","Ноябрь","Декабрь")
        return months[month.toInt()-1]
    }


    fun plusWeek(){
        if(weekNum.value!! < 16)_weekNum.value = weekNum.value!!.plus(1)
    }
    fun minusWeek(){
        if(weekNum.value!! > 0)_weekNum.value = weekNum.value!!.minus(1)
    }
    fun plusDay(){
        if(dayNum.value == 6){
            weekChangedByDay = true
            plusWeek()
            _dayNum.value = 1
        }
        else if(dayNum.value!! < 6  )_dayNum.value = _dayNum.value!!.plus(1)

    }
    fun minusDay() {
        if(dayNum.value == 1) {
            weekChangedByDay = true
            minusWeek()
            _dayNum.value = 6
        }
        else if(dayNum.value!! >1) _dayNum.value = _dayNum.value!!.minus(1)

    }

    fun onMessageShowed() {
        /**
         * Действия после того как сообщение показано
         */
        _isShowed.value = true
    }

    fun getLessons(): ArrayList<LessonEntity> {
        return if (weekNum.value != null && week.value?.oddWeekLessons != null)
            if (weekNum.value!! % 2 == 0) {
                week.value?.evenWeekLessons!!.getLessons(dayNum.value!!)
            } else {
                week.value?.oddWeekLessons!!.getLessons(dayNum.value!!)

            }
        else
            arrayListOf()
    }

}