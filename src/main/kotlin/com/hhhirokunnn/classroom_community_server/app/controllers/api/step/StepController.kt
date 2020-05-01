package com.hhhirokunnn.classroom_community_server.app.controllers.api.step

import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.StepRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.step.StepService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/steps")
class StepController(
    private val articleService: ArticleService,
    private val stepService: StepService
) {

    @PostMapping("")
    fun create(@RequestHeader authorization: String,
               @RequestBody @Valid param: StepRegisterParameter): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val article = articleService.findById(param.articleId)
        if(article.isEmpty) throw ArticleNotFoundError()
        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                data = stepService.save(param, article.get())),
            HttpStatus.OK)
    }

    @GetMapping("")
    fun findById(@RequestHeader authorization: String,
                 @RequestParam articleId: Long): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

//        val article = articleService.findById(articleId)
//        if(article.isEmpty) throw ArticleNotFoundError()
//        val step = stepService.findByArticle(articleId)
//        if(step.isEmpty) throw StepNotFoundError()

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                data = stepService.findByArticle(articleId)),
            HttpStatus.OK)
    }
}