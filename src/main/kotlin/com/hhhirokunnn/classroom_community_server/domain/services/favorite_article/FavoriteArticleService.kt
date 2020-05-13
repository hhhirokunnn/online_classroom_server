package com.hhhirokunnn.classroom_community_server.domain.services.favorite_article

import com.hhhirokunnn.classroom_community_server.app.models.responses.FavoriteArticleResponse
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.article.ArticleRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article.FavoriteArticleRepository
import org.springframework.stereotype.Service

@Service
class FavoriteArticleService(
    private val favoriteArticleRepository: FavoriteArticleRepository,
    private val articleRepository: ArticleRepository) {

    fun save(user: UserEntity, article: ArticleEntity): Boolean {
        favoriteArticleRepository.save(
                FavoriteArticleEntity(
                        user = user,
                        article = article))

        return true
    }

    fun findAllArticles(userId: Long): FavoriteArticleResponse {
        val favoriteArticles = favoriteArticleRepository.findByUserIdOrderByCreatedAtDesc(userId)
        val articles = articleRepository.findAllByIdIn(favoriteArticles.map { it.id })
        return FavoriteArticleResponse(
            count = articles.count(),
            articles = articles.map {
                FavoriteArticleResponse.FavoriteArticle(
                    articleId = it.id!!,
                    title = it.title)})
    }

    fun countAmountByArticleId(id: Long): Int = favoriteArticleRepository.countByArticleId(id)

    fun findByUserId(userId: Long): List<FavoriteArticleEntity> = favoriteArticleRepository.findByUserIdOrderByCreatedAtDesc(userId)
}