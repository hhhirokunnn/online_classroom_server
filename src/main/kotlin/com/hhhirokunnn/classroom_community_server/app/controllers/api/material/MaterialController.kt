package com.hhhirokunnn.classroom_community_server.app.controllers.api.material

import com.hhhirokunnn.classroom_community_server.app.models.parameters.MaterialRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.material.MaterialService
import com.hhhirokunnn.classroom_community_server.domain.services.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/materials")
class MaterialController(
        private val userService: UserService,
        private val articleService: ArticleService,
        private val materialService: MaterialService
) {

    @PostMapping
    fun create(@RequestHeader authorization: String,
               @RequestBody @Valid param: MaterialRegisterParameter): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = TokenService.doIdentifyToken(authorization, userService)

        val article = articleService.doFindByIdAndUserId(param.articleId, user.id!!)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = materialService.save(param, article)),
            HttpStatus.OK)
    }

    @GetMapping
    fun find(@RequestHeader authorization: String,
             @RequestParam articleId: Long): ResponseEntity<MyResponse> {

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = materialService.findByArticle(articleId)),
            HttpStatus.OK)
    }
}
