package com.hhhirokunnn.classroom_community_server.app.models.parameters

import org.springframework.web.multipart.MultipartFile

data class StepRegisterParameter(
    val articleId: Long,
    val description: String,
    val image: MultipartFile?
)
