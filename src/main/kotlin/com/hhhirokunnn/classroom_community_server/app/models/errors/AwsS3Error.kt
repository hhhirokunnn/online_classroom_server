package com.hhhirokunnn.classroom_community_server.app.models.errors

open class AwsS3Error: Exception()

data class AwsS3UploadError(
        val errorMessage: String = "画像のアップロードに失敗しました。",
        val error: Exception): AwsS3Error()

