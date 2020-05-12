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
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/steps")
class StepController(
    private val articleService: ArticleService,
    private val stepService: StepService
) {

    //    @PostMapping("/articles", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @CrossOrigin
    @PostMapping("")
    fun create(@RequestHeader authorization: String,
               @RequestParam("articleId") articleId: Long,
               @RequestParam("description") description: String,
               @RequestParam("image") image: MultipartFile?): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

//        val step: StepRegisterParameter
//        try {
//            val mapper = jacksonObjectMapper()
//            step = mapper.readValue(strJson)
//        } catch (e: Exception) {
//            throw StepParamParseError(error = e)
//        }
        val step = StepRegisterParameter(
            articleId = articleId,
            description = description)

        val article = articleService.findById(step.articleId)

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = stepService.save(step, image, article)),
            HttpStatus.OK)
    }

    @GetMapping("")
    fun findById(@RequestParam articleId: Long): ResponseEntity<MyResponse> {

        return ResponseEntity(
            SuccessResponse(
                message = "",
                status = 200,
                content = stepService.findByArticle(articleId)),
            HttpStatus.OK)
    }
}