package com.hhhirokunnn.classroom_community_server.domain.entities.favorite_comment

import com.hhhirokunnn.classroom_community_server.domain.entities.comment.CommentEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "favorite_comments")
class FavoriteCommentEntity (
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
        @GenericGenerator(name = "native",strategy = "native")
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        val user: UserEntity,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "comment_id", referencedColumnName = "id", nullable = false)
        val comment: CommentEntity,

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = LocalDateTime.now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = LocalDateTime.now()
)
