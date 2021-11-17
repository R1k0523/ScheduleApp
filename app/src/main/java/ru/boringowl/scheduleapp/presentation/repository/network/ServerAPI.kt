package ru.boringowl.scheduleapp.presentation.repository.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.boringowl.scheduleapp.presentation.repository.network.response.FullScheduleModel


interface ServerAPI {
    @GET("schedules/{name}")
    suspend fun lessons(
        @Path("name") text: String,
        ): Response<FullScheduleModel>
}
