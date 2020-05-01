package com.hhhirokunnn.classroom_community_server.app.models.responses

data class FavoriteArticleResponse(
        val count: Int,
        val articles: List<FavoriteArticle>
) {
    data class FavoriteArticle(
        val id: Long,
        val title: String
    )
}
