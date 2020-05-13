package com.hhhirokunnn.classroom_community_server.domain.repositories.comment

import com.hhhirokunnn.classroom_community_server.domain.entities.comment.CommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CommentRepository: JpaRepository<CommentEntity, Long> {

    fun findAllByArticleIdOrderByCreatedAtDesc(id: Long): List<CommentEntity>

    fun findByIdAndUserId(id: Long, userId: Long): Optional<CommentEntity>
}
