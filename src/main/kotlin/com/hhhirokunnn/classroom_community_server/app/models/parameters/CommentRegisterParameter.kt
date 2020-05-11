package com.hhhirokunnn.classroom_community_server.app.models.parameters

import org.springframework.web.multipart.MultipartFile

data class CommentRegisterParameter(
    val userId: Long,
    val articleId: Long,
    val content: String
)
