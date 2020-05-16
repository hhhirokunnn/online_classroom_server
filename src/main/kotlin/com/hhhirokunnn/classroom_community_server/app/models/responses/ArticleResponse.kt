package com.hhhirokunnn.classroom_community_server.app.models.responses

import java.time.LocalDateTime

data class ArticleResponse(
    val id: Long,
    val title: String,
    val summary: String,
    val estimatedTime: Int?,
    val memberUnit: Int?,
    val image: String?,
    val youtubeLink: String?,
    val count: Long,
    val createdAt: LocalDateTime
)
