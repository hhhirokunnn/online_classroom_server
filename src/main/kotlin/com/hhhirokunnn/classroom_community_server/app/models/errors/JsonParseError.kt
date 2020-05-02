package com.hhhirokunnn.classroom_community_server.app.models.errors

open class JsonParseError: Exception()

data class StepParamParseError(
        val errorMessage: String = "Step登録のパラメータが正しくありません。",
        val error: Exception): JsonParseError()

data class MaterialParamParseError(
        val errorMessage: String = "Material登録のパラメータが正しくありません。",
        val error: Exception): JsonParseError()

