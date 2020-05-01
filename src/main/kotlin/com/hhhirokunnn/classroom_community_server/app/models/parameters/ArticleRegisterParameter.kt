package com.hhhirokunnn.classroom_community_server.app.models.parameters

data class ArticleRegisterParameter(
    val title: String,
    val summary: String,
    val estimatedTime: Int?,
    val memberUnit: Int?,
    val image: String?,
    val youtubeLink: String?,
    val userId: Long
)
