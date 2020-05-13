package com.hhhirokunnn.classroom_community_server.domain.services.material

import com.hhhirokunnn.classroom_community_server.app.models.parameters.MaterialRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.MaterialResponse
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.material.MaterialEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.material.MaterialRepository
import org.springframework.stereotype.Service

@Service
class MaterialService(private val materialRepository: MaterialRepository) {

    fun save(param: MaterialRegisterParameter, article: ArticleEntity): MaterialResponse {
        val material = materialRepository.save(toEntity(param, article))
        return toResponse(material)
    }

    fun findByArticle(articleId: Long): List<MaterialResponse> {
        val materials = materialRepository.findAllByArticleIdOrderByIdAsc(articleId)
        return materials.map { toResponse(it) }
    }

    private fun toEntity(param: MaterialRegisterParameter, article: ArticleEntity) =
            MaterialEntity(
                    item = param.item,
                    itemUnit = param.itemUnit,
                    url = param.url,
                    preparation = param.preparation,
                    article = article)

    private fun toResponse(material: MaterialEntity) =
            MaterialResponse(
                    id = material.id!!,
                    preparation = material.preparation,
                    item = material.item,
                    itemUnit = material.itemUnit,
                    url = material.url)
}