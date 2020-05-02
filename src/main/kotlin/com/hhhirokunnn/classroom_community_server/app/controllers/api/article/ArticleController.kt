package com.hhhirokunnn.classroom_community_server.app.controllers.api.article

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hhhirokunnn.classroom_community_server.app.models.errors.*
import com.hhhirokunnn.classroom_community_server.app.models.parameters.ArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.parameters.FavoriteArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.parameters.StepRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.FavoriteArticleResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
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
import java.lang.Exception
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class ArticleController(
    private val articleService: ArticleService,
    private val userService: UserService,
    private val favoriteArticleService: FavoriteArticleService
) {

    @PostMapping("/articles")
    fun create(@RequestHeader authorization: String,
                @RequestParam("article") strJson: String,
                @RequestParam("image") image: MultipartFile?): ResponseEntity<ArticleEntity> {
//        TokenService.authenticateToken(authorization)

        val param: ArticleRegisterParameter
        try {
            val mapper = jacksonObjectMapper()
            param = mapper.readValue(strJson)
        } catch (e: Exception) {
            throw ArticleParamParseError(error = e)
        }

        val article = articleService.save(param, image)

        return ResponseEntity(article, HttpStatus.OK)
    }

    @GetMapping("/articles")
    fun findAll(@RequestHeader authorization: String): ResponseEntity<MyResponse> {
        TokenService.authenticateToken(authorization)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                data = articleService.findAll()),
            HttpStatus.OK)
    }

    @GetMapping("/markedArticles")
    fun findFavoriteArticles(@RequestHeader authorization: String, @RequestParam userId: Long): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val articles = articleService.findAllViaFavoriteArticle(userId)
        val favoriteArticles = FavoriteArticleResponse(
            count = articles.count(),
            articles = articles.map {
                FavoriteArticleResponse.FavoriteArticle(
                        id = it.id!!,
                        title = it.title)})

        return ResponseEntity(
                SuccessResponse(
                        message = "",
                        status = 200,
                        data = favoriteArticles),
                HttpStatus.OK)
    }

    @PostMapping("/markedArticles")
    fun createMarkedArticle(
            @RequestHeader authorization: String,
            @RequestBody param: FavoriteArticleRegisterParameter): FavoriteArticleEntity {
        val article = articleService.findById(param.articleId)
        val user = userService.findById(param.userId)

        if(user.isEmpty) throw UserNotFoundError()
        if(article.isEmpty) throw ArticleNotFoundError()

        return favoriteArticleService.save(user.get(), article.get())
    }
}