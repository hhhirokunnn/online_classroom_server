package com.hhhirokunnn.classroom_community_server.domain.services.step

import com.hhhirokunnn.classroom_community_server.app.models.parameters.StepRegisterParameter
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.step.StepEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.step.StepRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class StepService(private val stepRepository: StepRepository) {

    fun save(param: StepRegisterParameter, article: ArticleEntity): StepEntity {

        return stepRepository.save(
            StepEntity(
                    description = param.description,
                    image = "",
                    article = article
            ))
    }

    private fun uploadFile(image: MultipartFile): String {

        return "uploadFile"
    }

    fun findByArticle(articleId: Long): List<StepEntity> = stepRepository.findByArticleId(articleId)
}
