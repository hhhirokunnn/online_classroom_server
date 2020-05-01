package com.hhhirokunnn.classroom_community_server.domain.utils

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.DelegatingPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

object MyPasswordEncoder {

    fun run(rawPassword: String): String = delegatingPasswordEncoder().encode(rawPassword)

    fun matches(rawPassword: String, encryptedPassword: String) = delegatingPasswordEncoder().matches(rawPassword, encryptedPassword)

    private fun delegatingPasswordEncoder(): PasswordEncoder {
        val idForEncode = "bcrypt"
        val encoders: HashMap<String, PasswordEncoder> = hashMapOf(Pair(idForEncode, BCryptPasswordEncoder(10)))

        return DelegatingPasswordEncoder(idForEncode, encoders)
    }
}
