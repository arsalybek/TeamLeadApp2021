package com.example.teamleadapp.view.utils

import androidx.annotation.Keep
import com.google.gson.Gson
import okhttp3.ResponseBody

@Keep
class ErrorResponse(
    val message: String?,
    val description: String?,
    val fieldErrors: List<FieldError>?,
    val value: String?,
    val error_description: String?,
    val errorMessage: String?,
    val errorCode: String?
) {

    fun print(default: String = "default error message"): String =
        if (!fieldErrors.isNullOrEmpty()) {
            fieldErrors.mapNotNull { it.message }.joinToString(", ")
        } else {
            errorMessage ?: error_description ?: description ?: message ?: value ?: default
        }

    companion object {
        /**
         * Парсинг ответа сервера вручную в объект [ErrorResponse]
         */
        fun from(response: ResponseBody?): ErrorResponse? = try {
            Gson().fromJson(response?.charStream(), ErrorResponse::class.java)
                .apply { response?.close() }
        } catch (e: Exception) {
            null
        }
    }
}

fun ErrorResponse?.print(
    default: String = "default error message"
): String = this?.print(default) ?: default

data class FieldError(
    val type: String?,
    val objectName: String?,
    val field: String?,
    val message: String?,
    val documentReference: String?,
    val errorMessage: String?
)