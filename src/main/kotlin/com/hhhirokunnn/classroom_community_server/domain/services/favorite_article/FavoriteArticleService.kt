package com.hhhirokunnn.classroom_community_server.domain.services.favorite_article

import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.errors.FavoriteArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.responses.FavoriteArticleResponse
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.article.ArticleRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article.FavoriteArticleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class FavoriteArticleService(
    private val favoriteArticleRepository: FavoriteArticleRepository,
    private val articleRepository: ArticleRepository) {

    fun save(user: UserEntity, articleId: Long): FavoriteArticleResponse {
        val article = articleRepository.findById(articleId).orElseThrow { throw ArticleNotFoundError() }
        val favoriteArticle = favoriteArticleRepository.save(
                FavoriteArticleEntity(
                        user = user,
                        article = article))

        return FavoriteArticleResponse(
            count = 1,
            articles =  listOf(
                FavoriteArticleResponse.FavoriteArticle(
                    id = favoriteArticle.id!!,
                    articleId = favoriteArticle.article.id!!,
                    title = favoriteArticle.article.title)))
    }

    fun findAllArticles(userId: Long, from: Int, size: Int): FavoriteArticleResponse {
        val favoriteArticles = favoriteArticleRepository.findAllByUserIdOrderByCreatedAtDesc(userId, PageRequest.of(from, size))
        return FavoriteArticleResponse(
            count = favoriteArticles.count(),
            articles = favoriteArticles.map {
                FavoriteArticleResponse.FavoriteArticle(
                    id = it.id!!,
                    articleId = it.article.id!!,
                    title = it.article.title)})
    }

    fun delete(userId: Long, id: Long): Unit {
        val favArticle = favoriteArticleRepository.findByIdAndUserId(userId = userId, id = id).orElseThrow{ throw FavoriteArticleNotFoundError() }
        favoriteArticleRepository.delete(favArticle)
    }

    fun findByIdAndUserId(id: Long, userId: Long): FavoriteArticleEntity = favoriteArticleRepository.findByIdAndUserId(id, userId).orElseThrow { throw FavoriteArticleNotFoundError() }

    fun countAmountByArticleId(id: Long): Int = favoriteArticleRepository.countByArticleId(id)

    fun findByUserId(userId: Long): List<FavoriteArticleEntity> = favoriteArticleRepository.findByUserIdOrderByCreatedAtDesc(userId)

}