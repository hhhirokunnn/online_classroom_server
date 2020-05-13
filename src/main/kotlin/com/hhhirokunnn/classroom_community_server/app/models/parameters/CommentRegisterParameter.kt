package com.hhhirokunnn.classroom_community_server.app.models.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotBlank

data class CommentRegisterParameter(

    @field:NotBlank
    @JsonProperty("articleId")
    val articleId: Long,

    @field:NotBlank
    @JsonProperty("content")
    val content: String
)
