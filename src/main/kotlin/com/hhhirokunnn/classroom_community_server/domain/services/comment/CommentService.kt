package com.hhhirokunnn.classroom_community_server.domain.services.comment

import com.hhhirokunnn.classroom_community_server.app.models.errors.CommentNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.errors.UserNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.responses.CommentResponse
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.comment.CommentEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.comment.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(private val commentRepository: CommentRepository,
                     private val userRepository: UserRepository) {
    fun save(user: UserEntity, article: ArticleEntity, content: String): CommentResponse {
        val createdComment = commentRepository.save(
            CommentEntity(
                user = user,
                article = article,
                content = content))

        return CommentResponse(
            id = createdComment.id!!,
            userName = createdComment.user.name,
            content = createdComment.content,
            createdAt = createdComment.createdAt!!)
    }

    fun findAllByArticleIdOrderByCreatedAtDesc(articleId: Long): List<CommentEntity> {

        return commentRepository.findAllByArticleIdOrderByCreatedAtDesc(articleId)
    }

    @Transactional
    fun delete(commentId: Long, userId: Long): Unit {

        val user = userRepository.findById(userId).orElseThrow { throw UserNotFoundError() }
        val comment = commentRepository.findById(commentId).orElseThrow { throw CommentNotFoundError() }

        commentRepository.delete(comment)
    }
}
