package com.example.raj.arrpg.algebra.domain.model

import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.example.raj.arrpg.algebra.domain.exception.ApiException
import java.net.HttpURLConnection

sealed class PersonError {

    companion object {
        fun fromThrowable(e: Throwable): PersonError =
                when (e) {
                    is ApiException ->
                        when (e.httpCode) {
                            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundError
                            HttpURLConnection.HTTP_FORBIDDEN -> AuthenticationError
                            else -> UnknownServerError(Some(e))
                        }
                    else -> UnknownServerError((Some(e)))
                }
    }

    object AuthenticationError : PersonError()
    object NotFoundError : PersonError()
    data class UnknownServerError(val e: Option<Throwable> = None) : PersonError()
}