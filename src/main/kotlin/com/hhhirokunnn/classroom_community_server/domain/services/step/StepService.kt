package com.hhhirokunnn.classroom_community_server.domain.services.step

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import com.hhhirokunnn.classroom_community_server.app.models.errors.AwsS3UploadError
import com.hhhirokunnn.classroom_community_server.app.models.errors.FileSizeExceededError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.StepRegisterParameter
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.step.StepEntity
import com.hhhirokunnn.classroom_community_server.domain.repositories.step.StepRepository
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

@Service
class StepService(private val stepRepository: StepRepository) {

    fun save(param: StepRegisterParameter, image: MultipartFile?, article: ArticleEntity): StepEntity {

        return stepRepository.save(
            StepEntity(
                description = param.description,
                image = image?.let { uploadFile(it, param.articleId) },
                article = article
            ))
    }

    private fun uploadFile(image: MultipartFile, articleId: Long): String {
        val fileName = toFileName(articleId)

        val bucketName = "hirokuntest"
        val region = "ap-northeast-1"
        val accessKey = "AKIATZBVTE67QCO4NLFG"
        val secretKey = "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"
        val client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))).build()
        try {
            val file = convertToFile(image)
            client.putObject(PutObjectRequest(bucketName, "steps/${fileName}", file))
            file.delete()
        } catch(e: Exception) {
            throw AwsS3UploadError(error = e)
        }

        return "https://${bucketName}.s3-${region}.amazonaws.com/steps/${fileName}"
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

    private fun toFileName(articleId: Long) = "${articleId}_${LocalDateTime.now()}"

    fun findByArticle(articleId: Long): List<StepEntity> = stepRepository.findByArticleId(articleId)
}
