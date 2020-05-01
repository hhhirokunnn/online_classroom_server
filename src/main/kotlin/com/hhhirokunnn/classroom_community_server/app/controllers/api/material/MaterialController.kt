package com.hhhirokunnn.classroom_community_server.app.controllers.api.material

import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.MaterialRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.material.MaterialService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/materials")
class MaterialController(
        private val articleService: ArticleService,
        private val materialService: MaterialService
) {

    @PostMapping
    fun create(@RequestHeader authorization: String,
               @RequestBody @Valid param: MaterialRegisterParameter): ResponseEntity<MyResponse> {
        TokenService.authenticateToken(authorization)

        val article = articleService.findById(param.articleId)
        if(article.isEmpty) throw ArticleNotFoundError()

        return ResponseEntity(
                SuccessResponse(
                        message = "",
                        status = 200,
                        data = materialService.save(param, article.get())),
                HttpStatus.OK)
    }

    @GetMapping
    fun findByArticle(@RequestHeader authorization: String,
                      @RequestParam articleId: Long): ResponseEntity<MyResponse> {
        TokenService.authenticateToken(authorization)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                data = materialService.findByArticle(articleId)),
            HttpStatus.OK)
    }
}
