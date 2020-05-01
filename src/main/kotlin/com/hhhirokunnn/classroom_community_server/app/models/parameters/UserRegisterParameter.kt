package com.hhhirokunnn.classroom_community_server.app.models.parameters

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserRegisterParameter (
        @field:NotBlank
        @JsonProperty("name")
        val name: String,

        @field:Email(message = "アドレスの形式が正しくありません。")
        @field:NotBlank
        @JsonProperty("mail")
        var mail: String,

        @field:NotBlank
        @JsonProperty("password")
        val password: String
)
