package com.hhhirokunnn.classroom_community_server.app.models.errors


abstract class ArticleError: Exception() {
    abstract val errorMessage: String
}

data class ArticleNotFoundError(
    override val errorMessage: String = "記事が存在しません。"): ArticleError()
