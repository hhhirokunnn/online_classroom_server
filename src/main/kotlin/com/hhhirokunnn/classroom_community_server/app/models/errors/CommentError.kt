package com.hhhirokunnn.classroom_community_server.app.models.errors

abstract class CommentError: Exception() {
    abstract val errorMessage: String
}

data class CommentNotFound(
        override val errorMessage: String = "コメントが存在しません"): CommentError()

data class UserNotFound(
        override val errorMessage: String = "ユーザが存在しません"): CommentError()
