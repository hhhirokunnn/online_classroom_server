package com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article

import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FavoriteArticleRepository: JpaRepository<FavoriteArticleEntity, Long> {

    fun save(favoriteArticleEntity: FavoriteArticleEntity): FavoriteArticleEntity
    fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<FavoriteArticleEntity>
    fun findAllByUserIdOrderByCreatedAtDesc(userId: Long, pageable: Pageable): List<FavoriteArticleEntity>
    fun countByArticleId(id: Long): Int
    fun findAllByArticleId(articleId: Long): List<FavoriteArticleEntity>
    fun findByIdAndUserId(id: Long, userId: Long): Optional<FavoriteArticleEntity>
}
