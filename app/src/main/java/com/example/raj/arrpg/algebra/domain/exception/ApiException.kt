package com.example.raj.arrpg.algebra.domain.exception

class ApiException(
        val httpCode: Int,
        description: String,
        cause: Throwable
) : Exception(description, cause)