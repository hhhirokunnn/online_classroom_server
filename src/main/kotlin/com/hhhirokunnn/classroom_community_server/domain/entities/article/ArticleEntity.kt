package com.hhhirokunnn.classroom_community_server.domain.entities.article

import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.step.StepEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*

@Entity
@Table(name = "articles")
class ArticleEntity (
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    val id: Long? = null,

    @Column(name = "title", length = 500, nullable = false)
    val title: String,

    @Column(name = "summary", length = 500, nullable = false)
    val summary: String,

    @Column(name = "estimated_time", nullable = true)
    val estimatedTime: Int?,

    @Column(name = "member_unit", nullable = true)
    val memberUnit: Int?,

    @Column(name = "image", length = 100, nullable = true)
    val image: String?,

    @Column(name = "youtube_link", length = 100, nullable = true)
    val youtubeLink: String?,

//    @Column(name = "user_id", nullable = false)
//    val userId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    val user: UserEntity,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = now()
)
