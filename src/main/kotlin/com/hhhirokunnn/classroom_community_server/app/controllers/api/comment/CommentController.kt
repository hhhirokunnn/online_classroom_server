package com.hhhirokunnn.classroom_community_server.app.controllers.api.comment

import com.hhhirokunnn.classroom_community_server.app.models.errors.CommentError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.CommentRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.CommentResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.ErrorResponse
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
                        id = it.id!!,
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

        val user = TokenService.doIdentifyToken(authorization, userService)
        val article = articleService.doFindById(param.articleId)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = commentService.save(
                    user = user,
                    article = article,
                    content = param.content)),
            HttpStatus.OK)
    }

    @DeleteMapping("/{commentId}")
    fun delete(@RequestHeader authorization: String,
               @PathVariable("commentId") commentId: Long): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = TokenService.doIdentifyToken(authorization, userService)

        return try {
            commentService.delete(userId = user.id!!, commentId = commentId)
            ResponseEntity(SuccessResponse(message = "削除しました", status = 200, content = ""), HttpStatus.OK)
        } catch (e: CommentError) {
            ResponseEntity(
                    ErrorResponse(
                            message = "削除に失敗しました",
                            status = 400,
                            error = e.toString()),
                    HttpStatus.BAD_REQUEST)
        } catch (e: Exception) {
            ResponseEntity(
                    ErrorResponse(
                            message = "削除に失敗しました",
                            status = 500,
                            error = "Unknown"),
                    HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}