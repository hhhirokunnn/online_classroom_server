package com.hhhirokunnn.classroom_community_server.app.models.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class MaterialRegisterParameter(

    @field:NotBlank
    @JsonProperty("articleId")
    val articleId: Long,

    @field:NotBlank
    @JsonProperty("preparation")
    val preparation: String,

    @JsonProperty("item")
    val item: String?,

    @JsonProperty("itemUnit")
    val itemUnit: Int?,

    @JsonProperty("url")
    val url: String?
)
