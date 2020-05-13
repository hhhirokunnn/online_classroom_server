package com.hhhirokunnn.classroom_community_server.domain.repositories.article

import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ArticleRepository: CrudRepository<ArticleEntity, Long> {

    fun findAllByOrderByCreatedAtDesc(pageable: Pageable): List<ArticleEntity>

    fun findAllByIdInOrderByCreatedAtDesc(ids: List<Long>): List<ArticleEntity>

    fun findAllByIdIn(ids: List<Long?>): List<ArticleEntity>

    fun findByIdAndUserId(id: Long, userId: Long): Optional<ArticleEntity>

    @Query("select a from ArticleEntity a order by created_at desc")
    fun queryAll(): List<ArticleEntity>
}
