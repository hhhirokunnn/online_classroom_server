package com.hhhirokunnn.classroom_community_server.app.controllers.api.user

import com.auth0.jwt.JWT
import com.hhhirokunnn.classroom_community_server.app.models.errors.UserNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.LoginParameter
import com.hhhirokunnn.classroom_community_server.app.models.parameters.UserRegisterParameter
import com.hhhirokunnn.classroom_community_server.app.models.responses.*
import com.hhhirokunnn.classroom_community_server.app.utils.TokenService
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.services.user.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid


@RestController
@RequestMapping("/api")
class UserController(private val userService: UserService) {

    @PostMapping("/users")
    fun create(@RequestBody @Valid param: UserRegisterParameter): ResponseEntity<MyResponse> {
        val user = userService.save(param)
        return ResponseEntity(
            SuccessResponse(
                message = "登録できました。",
                content = LoginResponse(TokenService.createToken(user))
            ), HttpStatus.OK)
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid param: LoginParameter): ResponseEntity<MyResponse> {
        val user = userService.findByMailAndPassword(mail = param.mail, password = param.password)
        user?.let { return ResponseEntity(
            SuccessResponse(
                message = "ログインできました。",
                content = LoginResponse(TokenService.createToken(it))
            ),
//            attachAuthToHttpHeader(TokenService.createToken(it)),
            HttpStatus.OK)}

        return ResponseEntity(
                ErrorResponse("ログインに失敗しました。"),
                HttpStatus.UNAUTHORIZED) }

    @CrossOrigin("http://localhost:3000")
    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<MyResponse> =
        ResponseEntity(
            SuccessResponse<Array<String>>(
                message = "ログアウトできました。",
                content = arrayOf()),
            HttpStatus.OK)

    //FIXME test
    @GetMapping("/users")
    fun find(@RequestHeader authorization: String): ResponseEntity<MyResponse> {

        TokenService.authenticateToken(authorization)

        val user = TokenService.identifyToken(authorization, userService)

        if(user.isEmpty) throw UserNotFoundError()

        return ResponseEntity(
            SuccessResponse(
            message = "",
            content = UserResponse(
                    userName = user.get().name,
                    mail = user.get().mail)),
            HttpStatus.OK)
    }
//    private fun attachAuthToHttpHeader(bearerToken: String): HttpHeaders {
//        val responseHttpHeaders = HttpHeaders()
//        responseHttpHeaders.set("authorization", bearerToken)
//        return responseHttpHeaders
//    }
}
