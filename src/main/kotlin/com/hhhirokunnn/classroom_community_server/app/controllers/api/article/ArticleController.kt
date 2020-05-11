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
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
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

//    @PostMapping("/articles", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @CrossOrigin
    @PostMapping("/articles")
    fun create(@RequestHeader authorization: String,
                @RequestParam("title") title: String,
                @RequestParam("summary") summary: String,
                @RequestParam("userId") userId: Long,
                @RequestParam("estimatedTime") estimatedTime: Int?,
                @RequestParam("memberUnit") memberUnit: Int?,
                @RequestParam("youtubeLink") youtubeLink: String?,
                @RequestParam("image") image: MultipartFile): ResponseEntity<ArticleEntity> {
        TokenService.authenticateToken(authorization)
//        val param: ArticleRegisterParameter
//        try {
//            val mapper = jacksonObjectMapper()
//            param = mapper.readValue(strJson)
//        } catch (e: Exception) {
//            throw ArticleParamParseError(error = e)
//        }
        val param = ArticleRegisterParameter(
            title = title,
            summary = summary,
            estimatedTime = estimatedTime,
            memberUnit = memberUnit,
            youtubeLink = youtubeLink,
            userId = userId,
            image = image)

        val article = articleService.save(param)

        return ResponseEntity(article, HttpStatus.OK)
    }

    @GetMapping("/articles")
    fun findAll(@RequestParam from: Int, size: Int = 20): ResponseEntity<MyResponse> {
//        TokenService.authenticateToken(authorization)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = articleService.findAll(from, size)),
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
                content = favoriteArticles),
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