package com.hhhirokunnn.classroom_community_server.domain.services.article

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.errors.AwsS3UploadError
import com.hhhirokunnn.classroom_community_server.app.models.errors.FileSizeExceededError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.ArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.ArticleDetailResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.ArticleResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.MaterialResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.StepResponse
import com.hhhirokunnn.classroom_community_server.configs.AwsConfig
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.material.MaterialEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.step.StepEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.article.ArticleRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article.FavoriteArticleRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.material.MaterialRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.step.StepRepository
import com.hhhirokunnn.classroom_community_server.domain.services.S3Client
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.config.annotation.CorsRegistry
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.util.*

@Service
class ArticleService(private val articleRepository: ArticleRepository,
                     private val materialRepository: MaterialRepository,
                     private val stepRepository: StepRepository,
                     private val favoriteArticleRepository: FavoriteArticleRepository,
                     private val awsConfig: AwsConfig) {

    fun save(article: ArticleRegisterParameter): ArticleEntity {
        val fileName = article.image?.let { uploadFile(it, article.userId.toString()) }
        return articleRepository.save(toEntity(article, fileName))
    }

    fun findAll(from: Int, size: Int): List<ArticleResponse> {
        val articles = articleRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(from, size))
        return articles.map { toResponse(it) }
    }

    fun findDetailById(articleId: Long): ArticleDetailResponse {
        val materials = materialRepository.findAllByArticleIdOrderByIdAsc(articleId)
        val steps = stepRepository.findAllByArticleIdOrderByIdAsc(articleId)
        return toDetailResponse(
                article = doFindById(articleId),
                materials = materials,
                steps = steps)
    }

    fun doFindById(articleId: Long): ArticleEntity = articleRepository.findById(articleId).orElseThrow{ ArticleNotFoundError() }

    fun doFindByIdAndUserId(articleId: Long, userId: Long): ArticleEntity = articleRepository.findByIdAndUserId(articleId, userId).orElseThrow{ ArticleNotFoundError() }

    @Transactional
    fun delete(articleId: Long, userId: Long): Unit {
        val article = articleRepository.findByIdAndUserId(articleId, userId)
        val materials = materialRepository.findAllByArticleIdOrderByIdAsc(articleId)
        val favoriteArticles = favoriteArticleRepository.findAllByArticleId(articleId)
        val steps = stepRepository.findAllByArticleIdOrderByIdAsc(articleId)
        materials.map { materialRepository.delete(it) }
        steps.map { stepRepository.delete(it) }
        favoriteArticles.map { favoriteArticleRepository.delete(it) }
        article.map { articleRepository.delete(it) }
    }


    private fun toEntity(article: ArticleRegisterParameter, image: String?) =
        ArticleEntity(
            title = article.title,
            summary = article.summary,
            estimatedTime = article.estimatedTime,
            memberUnit = article.memberUnit,
            image = image,
            youtubeLink = article.youtubeLink,
            userId = article.userId
        )

    private fun toResponse(articleEntity: ArticleEntity) =
            ArticleResponse(
                    id = articleEntity.id!!,
                    title = articleEntity.title,
                    summary = articleEntity.summary,
                    estimatedTime = articleEntity.estimatedTime,
                    memberUnit = articleEntity.memberUnit,
                    image = articleEntity.image,
                    youtubeLink = articleEntity.youtubeLink,
                    createdAt = articleEntity.createdAt)

    private fun toMaterialsResponse(materials: List<MaterialEntity>) =
            materials.map {
                MaterialResponse(
                    id = it.id!!,
                    preparation = it.preparation,
                    item = it.item,
                    itemUnit = it.itemUnit,
                    url = it.url)}

    private fun toStepsResponse(steps: List<StepEntity>) =
            steps.map {
                StepResponse(
                    id = it.id!!,
                    description = it.description,
                    image = it.image)}

    private fun toDetailResponse(
            article: ArticleEntity,
            materials: List<MaterialEntity>,
            steps: List<StepEntity>) =
            ArticleDetailResponse(
                article = toResponse(article),
                materials = toMaterialsResponse(materials),
                steps = toStepsResponse(steps))

    private fun uploadFile(image: MultipartFile, imageId: String): String {
        return S3Client(awsConfig, awsConfig.articleBucketKey).upload(image, imageId)
    }
}