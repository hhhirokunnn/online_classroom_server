package com.hhhirokunnn.classroom_community_server.app.models.errors

open class AuthenticateError: Exception()

data class NoHeaderParameterError(
        val errorMessage: String = "リクエストが正しくありません。",
        val error: Exception): AuthenticateError()

data class NoBearerError(
        val errorMessage: String = "リクエストが正しくありません。",
        val error: Exception): AuthenticateError()
