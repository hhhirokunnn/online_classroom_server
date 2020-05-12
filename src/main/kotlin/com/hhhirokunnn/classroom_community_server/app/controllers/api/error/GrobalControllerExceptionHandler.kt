package com.hhhirokunnn.classroom_community_server.app.controllers.api.error

import com.hhhirokunnn.classroom_community_server.app.models.errors.ArticleError
import com.hhhirokunnn.classroom_community_server.app.models.errors.AuthenticateError
import com.hhhirokunnn.classroom_community_server.app.models.errors.JWTError
import com.hhhirokunnn.classroom_community_server.app.models.responses.ErrorResponse
import org.springframework.dao.DataAccessException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.sql.SQLException

@ControllerAdvice
class GrobalControllerExceptionHandler {

//    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "登録情報が不適切です。")
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun conflict(): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                message = "登録情報が不適切です",
                status = 409,
                error = DataIntegrityViolationException::class.toString()),
            HttpStatus.CONFLICT)
    }

    @ExceptionHandler(SQLException::class, DataAccessException::class)
    fun databaseError(): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                message = "アクセスが集中しています、時間をおいてからアクセスし直してください",
                status = 500,
                error = "Database Error"),
            HttpStatus.INTERNAL_SERVER_ERROR)
    }

//    @ExceptionHandler(JWTError::class)
//    fun tokenError(): ResponseEntity<ErrorResponse> {
//        return ResponseEntity(
//            ErrorResponse(
//                message = "ログインし直してください",
//                error = JWTError::class.toString()),
//            HttpStatus.UNAUTHORIZED)
//    }

    @ExceptionHandler(AuthenticateError::class)
    fun authenticateError(): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                message = "ログインし直してください",
                error = AuthenticateError::class.toString()),
            HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validationError(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                message = e.bindingResult.allErrors.map { error -> error.defaultMessage}.toString(),
                error = "Validation Error"),
            HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ArticleError::class)
    fun validationError(e: ArticleError): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                message = e.errorMessage,
                status = 400,
                error = "BadRequest"),
            HttpStatus.BAD_REQUEST)
    }

//    @ExceptionHandler(Exception::class)
//    fun serverError(): ResponseEntity<ErrorResponse> {
//        return ResponseEntity(ErrorResponse("時間をおいてからアクセスし直してください。"), HttpStatus.INTERNAL_SERVER_ERROR)
//    }
}