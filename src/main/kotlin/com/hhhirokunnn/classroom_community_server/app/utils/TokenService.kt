package com.hhhirokunnn.classroom_community_server.app.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.hhhirokunnn.classroom_community_server.app.models.errors.JWTTokenError
import com.hhhirokunnn.classroom_community_server.app.models.errors.NoBearerError
import com.hhhirokunnn.classroom_community_server.app.models.errors.TokenCreateError
import com.hhhirokunnn.classroom_community_server.app.models.responses.UserResponse
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.services.user.UserService
import java.util.*

object TokenService {

    private val PRIVATE_KEY = "secret"
    private val TOKEN_PREFIX = "Bearer "
    private val algorithm: Algorithm = Algorithm.HMAC256(PRIVATE_KEY)

    fun authenticateToken(bearerToken : String){
        if (!bearerToken.startsWith(TOKEN_PREFIX)) throw NoBearerError()
        try {
            val verifier = JWT.require(algorithm).withIssuer("auth0").build()
            val token = bearerToken.replaceFirst(TOKEN_PREFIX, "")
            verifier.verify(token)
        } catch(e : JWTVerificationException) {
            throw JWTTokenError(error = e)
        } catch(e :IllegalArgumentException) {
            throw JWTTokenError(error = e)
        }
    }

    fun createToken(user : UserEntity): String {
        lateinit var token : String
        try {
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            calendar.add(Calendar.HOUR, 24)
            val expireTime = calendar.time
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("id", user.id)
                    .withExpiresAt(expireTime)
                    .sign(algorithm)

        } catch (e : JWTCreationException) {
            throw TokenCreateError(error = e)
        }
        return TOKEN_PREFIX + token
    }

    fun doIdentifyToken(bearerToken: String, userService: UserService): UserEntity {
        val token = bearerToken.replaceFirst(TOKEN_PREFIX, "")
//        FIXME: Error handling
        val userId = JWT.decode(token).getClaim("id").asLong()

        return userService.doFindById(userId)
    }
}