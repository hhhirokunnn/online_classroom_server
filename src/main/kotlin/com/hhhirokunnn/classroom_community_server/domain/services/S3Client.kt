package com.hhhirokunnn.classroom_community_server.domain.services

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import com.hhhirokunnn.classroom_community_server.app.models.errors.AwsS3UploadError
import com.hhhirokunnn.classroom_community_server.app.models.errors.FileSizeExceededError
import com.hhhirokunnn.classroom_community_server.configs.AwsConfig
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

class S3Client(private val awsConfig: AwsConfig, private val bucketKey: String) {

    fun upload(image: MultipartFile, articleId: String): String =
        S3ClientImpl(awsConfig, bucketKey).upload(image, articleId)

    private class S3ClientImpl(private val awsConfig: AwsConfig, private val bucketKey: String) {
        fun upload(image: MultipartFile, imageId: String): String {
        val (region, bucketName, accessKey, secretKey) = awsConfig

        val client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(
                        AWSStaticCredentialsProvider(
                                BasicAWSCredentials(
                                        accessKey,
                                        secretKey))).build()

            val fileName = toFileName(imageId)
            try {
                val file = convertToFile(image)
            client.putObject(PutObjectRequest(bucketName, "${bucketKey}/${fileName}", file))
            file.delete()
            } catch(e: Exception) {
                throw AwsS3UploadError(error = e)
            }

            return "https://${bucketName}.s3-${region}.amazonaws.com/${bucketKey}/${fileName}"
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

        private fun toFileName(imageId: String) = "${imageId}_${LocalDateTime.now()}"
    }
}

