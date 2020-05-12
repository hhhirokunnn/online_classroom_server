package com.hhhirokunnn.classroom_community_server.app.models.responses

data class MaterialResponse(
    val id: Long,
    val preparation: String,
    val item: String?,
    val itemUnit: Int?,
    val url: String?
)
