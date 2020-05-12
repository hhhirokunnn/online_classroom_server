package com.hhhirokunnn.classroom_community_server.app.models.errors

import java.lang.Exception

sealed class ArticleRegisterParameterError: Exception() {
    abstract val columnName: String
    abstract val reason: String
}

class EmptyError(
    override val columnName: String,
    override val reason: String = "入力必須です"): ArticleRegisterParameterError()

class OverLengthError(
    override val columnName: String,
    private val maxLength: Int,
    override val reason: String = "${columnName}は${maxLength}文字以下で入力してください"): ArticleRegisterParameterError()

class UrlFormatError(
    override val columnName: String,
    override val reason: String = "${columnName}はURLの形式ではないです"): ArticleRegisterParameterError()
