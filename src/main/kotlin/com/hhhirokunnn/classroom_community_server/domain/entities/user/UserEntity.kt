package com.hhhirokunnn.classroom_community_server.domain.entities.user

import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(name = "users")
data class UserEntity(
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
        @GenericGenerator(name = "native",strategy = "native")
        val id: Long? = null,

        @Column(name = "name", length = 50, nullable = false)
        val name: String,

        @Column(name = "mail", length = 50, nullable = false, unique=true)
        val mail: String,

        @Column(name = "password", length = 100, nullable = false)
        val password: String,

        @Column(name = "created_at")
        val createdAt: LocalDateTime? = now(),

        @Column(name = "updated_at")
        val updatedAt: LocalDateTime? = now()
)
