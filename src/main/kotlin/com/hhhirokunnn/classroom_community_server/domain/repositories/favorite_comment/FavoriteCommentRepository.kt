package com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_comment

import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_comment.FavoriteCommentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FavoriteCommentRepository: JpaRepository<FavoriteCommentEntity, Long> {

    fun countById(id: Long): Int
}
