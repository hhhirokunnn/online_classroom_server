package com.hhhirokunnn.classroom_community_server.app.models.parameters

import com.hhhirokunnn.classroom_community_server.app.models.errors.*
import org.springframework.web.multipart.MultipartFile

data class ArticleRegisterParameter(
    val title: String,
    val summary: String,
    val estimatedTime: Int?,
    val memberUnit: Int?,
    val youtubeLink: String?,
    val userId: Long,
    val image: MultipartFile?
) {
    fun validate() = run {
        if (title.isEmpty()) throw EmptyError(columnName = "タイトル")
        if (title.length > 50) throw OverLengthError(columnName = "タイトル", maxLength = 50)
        if (summary.isEmpty()) throw EmptyError(columnName = "概要")
        if (summary.length > 50) throw OverLengthError(columnName = "概要", maxLength = 50)
        youtubeLink?.let {
            if (!it.startsWith("https://")) throw UrlFormatError(columnName = "youtubeURL")
        }
    }
}
