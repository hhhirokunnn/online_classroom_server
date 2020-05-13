package com.hhhirokunnn.classroom_community_server.domain.repositories.material

import com.hhhirokunnn.classroom_community_server.domain.entities.material.MaterialEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MaterialRepository: JpaRepository<MaterialEntity, Long> {

    fun findAllByArticleIdOrderByIdAsc(articleId: Long): List<MaterialEntity>
}
