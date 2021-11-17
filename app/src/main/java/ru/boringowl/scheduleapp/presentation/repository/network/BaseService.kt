package ru.boringowl.scheduleapp.presentation.repository.network

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import ru.boringowl.parapp.presentation.repository.network.exceptions.*
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseService {
    protected suspend fun<T: Any> createCall(call: suspend () -> Response<T>) : MyResult<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable){
            return MyResult.Error(mapToNetworkError(t))
        }
        if (response.isSuccessful) {
            if (response.body() != null)
                return MyResult.Success(response.body()!!)
        } else {
            val errorBody = response.errorBody()
            return if (errorBody != null){
                MyResult.Error(mapApiException(response.code()))
            } else MyResult.Error(mapApiException(0))
        }
        return MyResult.Error(HttpException(response))
    }
    private fun mapApiException(code: Int): Exception = when(code){
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            HttpURLConnection.HTTP_UNAUTHORIZED -> UnAuthorizedException()
            else -> UnknownException()
        }
    private fun mapToNetworkError(t: Throwable): Exception {
        Log.d("tex123t", t.message.toString()+ "123123123")
        return when (t) {
            is SocketTimeoutException -> SocketTimeoutException("Connection Timed Out")
            is UnknownHostException -> NoInternetException()
            else -> UnknownException()
        }
    }
}