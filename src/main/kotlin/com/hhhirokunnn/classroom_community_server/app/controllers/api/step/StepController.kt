package com.hhhirokunnn.classroom_community_server.app.controllers.api.step

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.annotation.*
//import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.*

import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.errors.StepParamParseError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.StepRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.MyResponse
import com.hhhirokunnn.classroom_community_server.app.models.responses.SuccessResponse
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.services.article.ArticleService
import com.hhhirokunnn.classroom_community_server.domain.services.step.StepService
import netscape.javascript.JSObject
import org.apache.tomcat.util.json.JSONParser
import org.springframework.format.Parser
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.lang.Exception

@RestController
@RequestMapping("/api/steps")
class StepController(
    private val articleService: ArticleService,
    private val stepService: StepService
) {

//    @PostMapping("")
//    fun create(@RequestHeader authorization: String,
//               @RequestParam @Valid param: StepRegisterParameter,
//                @RequestParam("image") image: MultipartFile?): ResponseEntity<MyResponse> {
//
//        TokenService.authenticateToken(authorization)
//
//        val article = articleService.findById(param.articleId)
//        if(article.isEmpty) throw ArticleNotFoundError()
//        return ResponseEntity(
//            SuccessResponse(
//                message = "",
//                status = 200,
//                data = stepService.save(param, image, article.get())),
//            HttpStatus.OK)
//    }

    @PostMapping("")
    fun create(@RequestHeader authorization: String,
               @RequestParam("step") strJson: String,
               @RequestParam("image") image: MultipartFile?): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val step: StepRegisterParameter
        try {
            val mapper = jacksonObjectMapper()
            step = mapper.readValue(strJson)
        } catch (e: Exception) {
            throw StepParamParseError(error = e)
        }

        val article = articleService.findById(step.articleId)
        if(article.isEmpty) throw ArticleNotFoundError()
        return ResponseEntity(
                SuccessResponse(
                        message = "",
                        status = 200,
                        data = stepService.save(step, image, article.get())),
                HttpStatus.OK)
    }

//    @PostMapping("")
//    fun create(@RequestHeader authorization: String,
//               @RequestParam("file") image: MultipartFile): ResponseEntity<String> {
//
//        TokenService.authenticateToken(authorization)
//
//        println(image)
//
//
////        val article = articleService.findById(param.articleId)
////        if(article.isEmpty) throw ArticleNotFoundError()
//        return ResponseEntity.noContent().build()
//    }

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