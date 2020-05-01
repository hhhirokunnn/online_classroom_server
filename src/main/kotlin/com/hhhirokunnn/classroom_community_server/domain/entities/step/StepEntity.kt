package com.hhhirokunnn.classroom_community_server.domain.entities.step

import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*

@Entity
@Table(name = "steps")
class StepEntity (
        @Id
        @GeneratedValue
        val id: Long? = null,

        @Column(name = "description", length = 500, nullable = true)
        val description: String,

        @Column(name = "image", length = 100, nullable = true)
        val image: String?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "article_id", referencedColumnName = "id")
        val article: ArticleEntity,

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = now()
)
