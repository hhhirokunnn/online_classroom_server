package com.hhhirokunnn.classroom_community_server.domain.services.article

import com.hhhirokunnn.classroom_community_server.app.models.parameters.ArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.article.ArticleRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article.FavoriteArticleRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.Column

@Service
class ArticleService(private val articleRepository: ArticleRepository,
                     private val favoriteArticleRepository: FavoriteArticleRepository) {

    fun save(article: ArticleRegisterParameter): ArticleEntity = articleRepository.save(toEntity(article))
    fun findAll(): List<ArticleEntity> = articleRepository.findAllByOrderByCreatedAtDesc()
    fun findById(articleId: Long): Optional<ArticleEntity> = articleRepository.findById(articleId)

    fun findAllViaFavoriteArticle(userId: Long): List<ArticleEntity> {
        val favoriteArticles = favoriteArticleRepository.findByUserId(userId)
        return articleRepository.findAllByIdIn(favoriteArticles.map { it.id })
    }
    fun findAllByIdInOrderByCreatedAtDesc(ids: List<Long>): List<ArticleEntity> = articleRepository.findAllByIdInOrderByCreatedAtDesc(ids)
//    fun queryAll(): List<ArticleEntity> = articleRepository.queryAll()
//    fun findAllByIdIn(): List<ArticleEntity> = articleRepository.findAllByIdIn(listOf(1,2,3))
//    fun findAllByOrderByCreatedAtDesc(): List<ArticleEntity> = articleRepository.findAllByOrderByCreatedAtDesc()


    private fun toEntity(article: ArticleRegisterParameter) = ArticleEntity(
        title = article.title,
        summary = article.summary,
        estimatedTime = article.estimatedTime,
        memberUnit = article.memberUnit,
        image = article.image,
        youtubeLink = article.youtubeLink,
        userId = article.userId
    )
}