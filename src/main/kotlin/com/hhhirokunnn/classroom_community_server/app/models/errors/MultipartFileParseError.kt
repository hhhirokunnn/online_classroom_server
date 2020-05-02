package com.hhhirokunnn.classroom_community_server.app.models.errors

open class MultipartFileParseError: Exception()

data class FileSizeExceededError(
        val errorMessage: String = "ファイルサイズが大きいです。",
        val error: Exception): JWTError()

data class FileFormatIncorrectError(
        val errorMessage: String = "ファイルのフォーマットが違います。",
        val error: Exception): JWTError()
