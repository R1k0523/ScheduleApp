package ru.boringowl.scheduleapp.presentation.view.utils

import android.content.Context

class PrefsUtils(context: Context) {
    val prefs = context.getSharedPreferences("default", Context.MODE_PRIVATE)

    fun getGroup() : String? = prefs.getString("group", null)
    fun isGroupStored() : Boolean = prefs.getString("group", null) != null

    fun provideGroup(group: String) {
        prefs.edit().putString("group", group).apply()
    }
    fun deleteGroup() {
        prefs.edit().remove("group").apply()
    }
}