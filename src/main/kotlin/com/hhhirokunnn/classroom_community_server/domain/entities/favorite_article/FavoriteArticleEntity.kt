package com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article

import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*

@Entity
@Table(name = "favorite_articles")
class FavoriteArticleEntity (
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
        @GenericGenerator(name = "native",strategy = "native")
        val id: Long? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
        val user: UserEntity,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
        val article: ArticleEntity,

//        @Column(name = "user_id")
//        val userId: Long,
//
//        @Column(name = "article_id")
//        val articleId: Long,

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = now()
)
