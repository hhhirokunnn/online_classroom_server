package com.hhhirokunnn.classroom_community_server.domain.entities.material

import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*

@Entity
@Table(name = "materials")
class MaterialEntity (
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
        @GenericGenerator(name = "native",strategy = "native")
        val id: Long? = null,

        @Column(name = "item", length = 100, nullable = false)
        val item: String?,

        @Column(name = "item_unit", nullable = true)
        val itemUnit: Int?,

        @Column(name = "url", length = 500, nullable = true)
        val url: String?,

        @Column(name = "preparation", length = 500, nullable = false)
        val preparation: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "article_id", referencedColumnName = "id")
        val article: ArticleEntity,

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = now()
)
