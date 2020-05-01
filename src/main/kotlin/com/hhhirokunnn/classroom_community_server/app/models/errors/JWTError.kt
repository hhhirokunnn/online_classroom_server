package com.hhhirokunnn.classroom_community_server.app.models.errors

open class JWTError: Exception()

data class JWTTokenError(
        val errorMessage: String = "トークンが正しくありません。",
        val error: Exception): JWTError()

data class TokenCreateError(
        val errorMessage: String = "トークン発行でエラーが発生しました。",
        val error: Exception): JWTError()
