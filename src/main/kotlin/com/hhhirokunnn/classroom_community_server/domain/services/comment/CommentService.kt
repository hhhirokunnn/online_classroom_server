package com.hhhirokunnn.classroom_community_server.domain.services.comment

import com.hhhirokunnn.classroom_community_server.app.models.responses.CommentResponse
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.comment.CommentEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.comment.CommentRepository
import org.springframework.stereotype.Service
import javax.xml.stream.events.Comment

@Service
class CommentService(private val commentRepository: CommentRepository) {
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
}
