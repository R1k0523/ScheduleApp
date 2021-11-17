package ru.boringowl.scheduleapp.presentation.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.boringowl.scheduleapp.presentation.repository.network.ServerAPI
import ru.boringowl.scheduleapp.presentation.repository.network.ServerRepository
import ru.boringowl.scheduleapp.presentation.repository.network.ServerService

val networkModule = module {
    single { okhttpClient() }
    single { retrofit(get(), "https://schedule-spring.herokuapp.com/api/") }
    single { api(get()) }
    single { service(get()) }
    single { provideRepository(get()) }
}


fun okhttpClient() : OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    return OkHttpClient.Builder().addInterceptor(interceptor).build()
}

fun retrofit(okHttpClient: OkHttpClient, url: String) : Retrofit = Retrofit.Builder()
    .baseUrl(url)
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

fun api(retrofit: Retrofit): ServerAPI = retrofit.create(ServerAPI::class.java)
fun service(api: ServerAPI) : ServerService = ServerService(api)

fun provideRepository(serverService: ServerService) : ServerRepository = ServerRepository(serverService)