package com.hhhirokunnn.classroom_community_server.app.models.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CommentRegisterParameter(

    @field:NotNull
    @JsonProperty("articleId")
    val articleId: Long,

    @field:NotBlank
    @JsonProperty("content")
    val content: String
)
