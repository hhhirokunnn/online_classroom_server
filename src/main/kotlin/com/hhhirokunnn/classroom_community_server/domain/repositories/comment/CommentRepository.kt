package com.hhhirokunnn.classroom_community_server.domain.repositories.comment

import com.hhhirokunnn.classroom_community_server.domain.entities.comment.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommentRepository: JpaRepository<CommentEntity, Long> {

    fun findAllByArticleIdOrderByCreatedAtDesc(id: Long): List<CommentEntity>
}
