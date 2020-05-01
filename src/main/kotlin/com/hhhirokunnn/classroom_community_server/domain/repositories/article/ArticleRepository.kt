package com.hhhirokunnn.classroom_community_server.domain.repositories.article

import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ArticleRepository: JpaRepository<ArticleEntity, Long> {

    fun findAllByOrderByCreatedAtDesc(): List<ArticleEntity>
    fun findAllByIdInOrderByCreatedAtDesc(ids: List<Long>): List<ArticleEntity>
    fun findAllByIdIn(ids: List<Long?>): List<ArticleEntity>

    @Query("select a from ArticleEntity a order by created_at desc")
    fun queryAll(): List<ArticleEntity>
}
