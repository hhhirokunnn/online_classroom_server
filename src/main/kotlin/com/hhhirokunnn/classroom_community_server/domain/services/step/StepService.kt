package com.hhhirokunnn.classroom_community_server.domain.services.step

import com.hhhirokunnn.classroom_community_server.app.models.parameters.StepRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.ArticleResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.StepResponse
import com.hhhirokunnn.classroom_community_server.configs.AwsConfig
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.step.StepEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.step.StepRepository
import com.hhhirokunnn.classroom_community_server.domain.services.S3Client
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class StepService(private val stepRepository: StepRepository,
                  private val awsConfig: AwsConfig) {

    fun save(param: StepRegisterParameter, image: MultipartFile?, article: ArticleEntity): StepResponse {
        val step = stepRepository.save(
            StepEntity(
                description = param.description,
                image = image?.let { uploadFile(it, param.articleId.toString())},
                article = article))
        return StepResponse(
                id = step.id!!,
                description = step.description,
                image = step.image)}

    fun findByArticle(articleId: Long): List<StepResponse> {
        val steps = stepRepository.findAllByArticleIdOrderByIdAsc(articleId)
        return steps.map {
            StepResponse(
                id = it.id!!,
                description = it.description,
                image = it.image)}}

    private fun uploadFile(image: MultipartFile, imageId: String): String {
        return S3Client(awsConfig, awsConfig.stepBucketKey).upload(image, imageId)}
}
