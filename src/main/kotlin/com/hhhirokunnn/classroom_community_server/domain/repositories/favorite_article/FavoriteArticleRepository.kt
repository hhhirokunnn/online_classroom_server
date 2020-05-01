package com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article

import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FavoriteArticleRepository: JpaRepository<FavoriteArticleEntity, Long> {

    fun save(favoriteArticleEntity: FavoriteArticleEntity): FavoriteArticleEntity
    fun findByUserId(userId: Long): List<FavoriteArticleEntity>
}
