package com.hhhirokunnn.classroom_community_server.app.controllers.api.article

import com.hhhirokunnn.classroom_community_server.app.models.errors.*
import com.hhhirokunnn.classroom_community_server.app.models.parameters.ArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.parameters.FavoriteArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.ErrorResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.FavoriteArticleResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.services.article.ArticleRegisterParameterValidator
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.entities.article.ArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.favorite_article.FavoriteArticleEntity
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.favorite_article.FavoriteArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/articles")
class ArticleController(
    private val articleService: ArticleService,
    private val userService: UserService,
    private val favoriteArticleService: FavoriteArticleService
) {

    @CrossOrigin
    @PostMapping
    fun create(@RequestHeader authorization: String,
               @RequestParam("title") title: String,
               @RequestParam("summary") summary: String,
               @RequestParam("estimatedTime") estimatedTime: Int?,
               @RequestParam("memberUnit") memberUnit: Int?,
               @RequestParam("youtubeLink") youtubeLink: String?,
               @RequestParam("image") image: MultipartFile?): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = TokenService.doIdentifyToken(authorization, userService)

        val param = ArticleRegisterParameter(
            title = title,
            summary = summary,
            estimatedTime = estimatedTime,
            memberUnit = memberUnit,
            youtubeLink = youtubeLink,
            userId = user.id!!,
            image = image)

        val validator = ArticleRegisterParameterValidator(
                articleService = articleService, token = authorization, param = param)

        val (responseContent, responseStatus) = validator.validate()

        return ResponseEntity(responseContent, responseStatus)
    }

//    TODO: 論理削除
    @DeleteMapping
    fun delete(@RequestHeader authorization: String,
               @PathVariable("articleId") articleId: Long): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = TokenService.doIdentifyToken(authorization, userService)
        val article = articleService.doFindByIdAndUserId(articleId, user.id!!)

        articleService.delete(articleId = article.id!!, userId = user.id)

    return ResponseEntity(
            SuccessResponse(
                    message = "記事を削除しました",
                    status = 200,
                    content = ""),
            HttpStatus.OK)
    }

    @GetMapping
    fun findAll(@RequestParam from: Int, size: Int = 20): ResponseEntity<MyResponse> {

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = articleService.findAll(from, size)),
            HttpStatus.OK)
    }

    @GetMapping("/{articleId}")
    fun show(@PathVariable("articleId") articleId: Long): ResponseEntity<MyResponse> {
        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = articleService.findDetailById(articleId)),
            HttpStatus.OK)
    }
}