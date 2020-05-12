package com.hhhirokunnn.classroom_community_server.app.services.article

import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleRegisterParameterError
import com.hhhirokunnn.classroom_community_server.app.models.errors.AuthenticateError
import com.hhhirokunnn.classroom_community_server.app.models.errors.JWTTokenError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.ArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.ErrorResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import org.springframework.http.HttpStatus

class ArticleRegisterParameterValidator(
        private val articleService: ArticleService,
        private val token: String,
        private val param: ArticleRegisterParameter
) {

    fun validate(): Pair<MyResponse, HttpStatus> = try {
        TokenService.authenticateToken(token)
        param.validate()
        Pair(SuccessResponse(
                message = "",
                status = 200,
                content = articleService.save(param)),
             HttpStatus.OK)
    } catch (e: ArticleRegisterParameterError) {
        Pair(ErrorResponse(
                message = e.reason,
                status = 400,
                error = e.toString()),
             HttpStatus.BAD_REQUEST)
    } catch (e: JWTTokenError) {
        Pair(ErrorResponse(
                message = e.errorMessage,
                status = 400,
                error = e.toString()),
             HttpStatus.BAD_REQUEST)
    } catch (e: AuthenticateError) {
        Pair(ErrorResponse(
                message = e.reason,
                status = 400,
                error = e.toString()),
             HttpStatus.BAD_REQUEST)}
}