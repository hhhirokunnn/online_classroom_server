package com.hhhirokunnn.classroom_community_server.domain.services.article

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.errors.AwsS3UploadError
import com.hhhirokunnn.classroom_community_server.app.models.errors.FileSizeExceededError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.ArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.article.ArticleRepository
import com.hhhirokunnn.classroom_community_server.domain.repositories.favorite_article.FavoriteArticleRepository
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.util.*

@Service
class ArticleService(private val articleRepository: ArticleRepository,
                     private val favoriteArticleRepository: FavoriteArticleRepository) {

    fun save(article: ArticleRegisterParameter): ArticleEntity {
        val fileName = article.image?.let { uploadFile(it) }
        return articleRepository.save(toEntity(article, fileName))
    }

    fun findAll(from: Int, size: Int): List<ArticleEntity> =
        articleRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(from, size))

    fun findById(articleId: Long): ArticleEntity =
        articleRepository.findById(articleId).orElseThrow{ ArticleNotFoundError() }

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

    private fun uploadFile(image: MultipartFile): String {
        val fileName = toFileName()

        val bucketName = "hirokuntest"
        val region = "ap-northeast-1"
        val accessKey = "AKIATZBVTE67QCO4NLFG"
        val secretKey = "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"
        val client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))).build()
        try {
            val file = convertToFile(image)
            client.putObject(PutObjectRequest(bucketName, "articles/${fileName}", file))
            file.delete()
        } catch(e: Exception) {
            throw AwsS3UploadError(error = e)
        }

        return "https://${bucketName}.s3-${region}.amazonaws.com/articles/${fileName}"
    }

    private fun convertToFile(mulFile: MultipartFile): File {
        val convFile = File(mulFile.originalFilename!!)
        convFile.createNewFile()
        val fos = FileOutputStream(convFile)
        try {
            fos.write(mulFile.bytes)
            fos.close()
        } catch(e: FileSizeLimitExceededException) {
            throw FileSizeExceededError(error = e)
        }

        return convFile
    }

    private fun toFileName() = "${LocalDateTime.now()}"
}