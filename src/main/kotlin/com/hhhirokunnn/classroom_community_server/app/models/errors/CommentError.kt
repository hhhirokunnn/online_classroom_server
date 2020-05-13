package com.hhhirokunnn.classroom_community_server.app.models.errors

abstract class CommentError: Exception() {
    abstract val errorMessage: String
}

data class CommentNotFoundError(
        override val errorMessage: String = "コメントが存在しません"): CommentError()
