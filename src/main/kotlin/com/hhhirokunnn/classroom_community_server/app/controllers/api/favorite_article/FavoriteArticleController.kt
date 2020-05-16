package com.hhhirokunnn.classroom_community_server.app.controllers.api.favorite_article

import com.hhhirokunnn.classroom_community_server.app.models.parameters.FavoriteArticleRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.favorite_article.FavoriteArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/markedArticles")
class FavoriteArticleController(
    private val articleService: ArticleService,
    private val userService: UserService,
    private val favoriteArticleService: FavoriteArticleService
) {
    @GetMapping
    fun find(@RequestHeader authorization: String,
             @RequestParam from: Int, size: Int): ResponseEntity<MyResponse> {
        val user = TokenService.doIdentifyToken(authorization, userService)
        val articles = favoriteArticleService.findAllArticles(
                userId = user.id!!,
                from = from,
                size = size)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = articles),
            HttpStatus.OK)
    }

    @PostMapping
    fun create(@RequestHeader authorization: String,
               @RequestBody param: FavoriteArticleRegisterParameter): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = TokenService.doIdentifyToken(authorization, userService)

        return ResponseEntity(
                SuccessResponse(
                        message = "お気に入りに登録しました",
                        status = 200,
                        content = favoriteArticleService.save(user, param.articleId)),
                HttpStatus.OK)
    }

    @CrossOrigin
    @DeleteMapping("/{favoriteArticleId}")
    fun delete(@RequestHeader authorization: String,
               @PathVariable("favoriteArticleId") favoriteArticleId: Long): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = TokenService.doIdentifyToken(authorization, userService)

        favoriteArticleService.delete(user.id!!, favoriteArticleId)

        return ResponseEntity(
                SuccessResponse(
                        message = "お気に入りをやめました",
                        status = 200,
                        content = ""),
                HttpStatus.OK)
    }
}