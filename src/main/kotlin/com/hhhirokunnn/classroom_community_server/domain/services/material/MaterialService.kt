package com.hhhirokunnn.classroom_community_server.domain.services.material

import com.hhhirokunnn.classroom_community_server.app.models.parameters.MaterialRegisterParameter
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.material.MaterialEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.material.MaterialRepository
import org.springframework.stereotype.Service

@Service
class MaterialService(private val materialRepository: MaterialRepository) {

    fun save(param: MaterialRegisterParameter, article: ArticleEntity): MaterialEntity {
        return materialRepository.save(
            MaterialEntity(
                item = param.item,
                itemUnit = param.itemUnit,
                url = param.url,
                preparation = param.preparation,
                article = article
            )
        )
    }

    fun findByArticle(articleId: Long) = materialRepository.findAllByArticleId(articleId)
}