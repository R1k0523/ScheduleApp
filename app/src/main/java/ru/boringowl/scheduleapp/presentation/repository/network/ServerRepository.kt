package ru.boringowl.scheduleapp.presentation.repository.network

import ru.boringowl.scheduleapp.presentation.repository.network.response.FullScheduleModel

class ServerRepository(private val service: ServerService) {
    suspend fun getLessons(text: String) : FullScheduleModel {
        return when(val result = service.fetchLessons(text)){
            is MyResult.Success -> result.data
            is MyResult.Error -> throw result.error
        }
    }
}
