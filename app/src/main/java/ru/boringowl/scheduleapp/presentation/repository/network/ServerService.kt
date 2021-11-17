package ru.boringowl.scheduleapp.presentation.repository.network

import ru.boringowl.scheduleapp.presentation.repository.network.response.FullScheduleModel

class ServerService(private val api: ServerAPI) : BaseService() {
    suspend fun fetchLessons(text: String): MyResult<FullScheduleModel> =
        createCall { api.lessons(text = text) }
}
