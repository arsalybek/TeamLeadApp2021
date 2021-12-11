package com.example.teamleadapp.di

import com.example.teamleadapp.di.RequestResult.Error
import com.example.teamleadapp.view.utils.ErrorResponse
import com.google.gson.JsonParseException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import java.io.EOFException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


interface CoroutineCaller {
    suspend fun <T> apiCall(
        customErrorHandler: ((ErrorResponse) -> Error)? = null,
        request: suspend () -> T
    ): RequestResult<T>

    suspend fun <T> apiCallStrict(
        customErrorHandler: ((ErrorResponse) -> Error)? = null,
        request: suspend () -> T
    ): T
}


object ApiCaller: CoroutineCaller {

    private fun handleException(
        e: Exception,
        customErrorHandler: ((ErrorResponse) -> Error)? = null
    ): Error = when (e) {
        is HttpException -> handleHttpException(e, customErrorHandler)
        is SocketTimeoutException -> {
            Error("mobile request timeout")
        }
        is SSLHandshakeException -> {
            Error("mobile request ssl error")
        }
        is JsonParseException -> {
            Error("mobile request json error")
        }
        is EOFException -> {
            Error("mobile request Eof error")
        }
        is ConnectException,
        is UnknownHostException -> {
            Error("mobile errors with internet")
        }
        is CancellationException -> {
            Error("Запрос отменён")
        }
        is RequestError -> {
            Error(e.message, e.code)
        }
        else -> {
            Error("mobile common error: ${e.javaClass.simpleName} ${e.localizedMessage}")
        }
    }

    private fun handleHttpException(
        e: HttpException,
        customErrorHandler: ((ErrorResponse) -> Error)? = null
    ) = when (e.code()) {
        HttpURLConnection.HTTP_UNAUTHORIZED -> {
            val reason = "mobile session expired"
            val errorBody = e.response()?.errorBody()
            Error(ErrorResponse.from(errorBody)!!.print(default = reason), e.code())
        }
        HttpURLConnection.HTTP_BAD_REQUEST -> {
            val error = ErrorResponse.from(e.response()?.errorBody())
            Error(error!!.print(), e.code(), error.errorCode)
        }
        HttpURLConnection.HTTP_UNAVAILABLE,
        HttpURLConnection.HTTP_BAD_GATEWAY -> {
            Error("mobile server maintenance", e.code())
        }
        else -> {
            val error = ErrorResponse.from(e.response()?.errorBody())
            if (customErrorHandler != null && error != null) {
                customErrorHandler.invoke(error)
            } else {
                Error(
                    error?.print(default = "default error message ${e.code()}").orEmpty(),
                    e.code()
                )
            }
        }
    }

    override suspend fun <T> apiCall(
        customErrorHandler: ((ErrorResponse) -> Error)?,
        request: suspend () -> T
    ): RequestResult<T> = try {
        coroutineScope {
            RequestResult.Success(request.invoke())
        }
    } catch (e: Exception) {
        handleException(e, customErrorHandler)
    }

    override suspend fun <T> apiCallStrict(
        customErrorHandler: ((ErrorResponse) -> Error)?,
        request: suspend () -> T
    ): T = try {
        coroutineScope { request.invoke() }
    } catch (e: Exception) {
        val (message, code) = handleException(e, customErrorHandler)
        throw RequestError(message, code)
    }
}

sealed class RequestResult<out T : Any?> {
    data class Success<out T : Any?>(val result: T) : RequestResult<T>()
    data class Error(val error: String, val code: Int = 0, val errorCode: String? = null) : RequestResult<Nothing>()
}

class RequestError(override val message: String, val code: Int = 0, val errorCode: String? = null) : Exception(message)
