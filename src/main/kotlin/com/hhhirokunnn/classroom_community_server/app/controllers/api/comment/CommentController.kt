package com.hhhirokunnn.classroom_community_server.app.controllers.api.comment

import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.errors.UserNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.CommentRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.CommentResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.comment.CommentService
import com.hhhirokunnn.classroom_community_server.domain.services.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentController(
        private val commentService: CommentService,
        private val userService: UserService,
        private val articleService: ArticleService
) {
    @GetMapping
    fun findAll(@RequestParam articleId: Long): ResponseEntity<MyResponse> {

        val comments = commentService.findAllByArticleIdOrderByCreatedAtDesc(articleId)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = comments.map {
                    CommentResponse(
                        userName = it.user.name,
                        content = it.content,
                        createdAt = it.createdAt!!
                    )}),
            HttpStatus.OK)
    }

    @PostMapping
    fun create(@RequestHeader authorization: String,
               @RequestBody param: CommentRegisterParameter): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = userService.findById(param.userId)
        val article = articleService.findById(param.articleId)

        if(user.isEmpty) throw UserNotFoundError()
        if(article.isEmpty) throw ArticleNotFoundError()

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = commentService.save(
                    user = user.get(),
                    article = article.get(),
                    content = param.content)),
            HttpStatus.OK)
    }
}