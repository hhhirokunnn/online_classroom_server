package com.hhhirokunnn.classroom_community_server.domain.repositories.step

import com.hhhirokunnn.classroom_community_server.domain.entities.step.StepEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StepRepository: JpaRepository<StepEntity, Long> {

    fun findByArticleId(articleId: Long): List<StepEntity>
}
