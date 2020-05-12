package com.hhhirokunnn.classroom_community_server.app.models.errors

sealed class AuthenticateError: Exception() {
    abstract val reason: String
}

class NoHeaderParameterError(
    override val reason: String = "リクエストが正しくありません。"): AuthenticateError()

class NoBearerError(
    override val reason: String = "リクエストが正しくありません。"): AuthenticateError()
