package com.hhhirokunnn.classroom_community_server.app.models.errors


abstract class FavoriteArticleError: Exception() {
    abstract val errorMessage: String
}

data class FavoriteArticleNotFoundError(
    override val errorMessage: String = "記事が存在しません。"): ArticleError()
