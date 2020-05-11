package com.hhhirokunnn.classroom_community_server.app.models.responses

import java.time.LocalDateTime

data class CommentResponse(
    val content: String,
    val userName: String,
    val createdAt: LocalDateTime
)
