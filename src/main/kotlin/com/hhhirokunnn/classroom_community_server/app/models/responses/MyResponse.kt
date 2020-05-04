package com.hhhirokunnn.classroom_community_server.app.models.responses

interface MyResponse {
    val message: String
    val status: Int
}

data class ErrorResponse(
    override val message: String,
    override val status: Int = 400,
    val error: String = "Unknown Error"
): MyResponse

data class SuccessResponse<T>(
    override val message: String,
    override val status: Int = 200,
    val content: T
): MyResponse
