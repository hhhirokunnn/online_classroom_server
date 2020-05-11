package com.hhhirokunnn.classroom_community_server.domain.services.favorite_comment

import com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_comment.FavoriteCommentRepository
import org.springframework.stereotype.Service

@Service
class FavoriteCommentService(private val favoriteCommentRepository: FavoriteCommentRepository) {
}
