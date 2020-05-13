package com.hhhirokunnn.classroom_community_server.app.models.responses

data class ArticleDetailResponse(
    val article: ArticleResponse,
    val materials: List<MaterialResponse>,
    val steps: List<StepResponse>
)