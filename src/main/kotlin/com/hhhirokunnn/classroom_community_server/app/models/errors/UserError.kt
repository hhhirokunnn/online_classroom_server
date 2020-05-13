package com.hhhirokunnn.classroom_community_server.app.models.errors

import java.lang.Exception

abstract class UserError: Exception() {
    abstract val errorMessage: String
}

data class UserNotFoundError(
    override val errorMessage: String = "ユーザが見つかりませんでした"
): UserError()