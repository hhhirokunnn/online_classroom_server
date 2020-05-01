package com.hhhirokunnn.classroom_community_server.app.models.parameters

data class MaterialRegisterParameter(
    val articleId: Long,
    val preparation: String,
    val item: String?,
    val itemUnit: Int?,
    val url: String?
)
