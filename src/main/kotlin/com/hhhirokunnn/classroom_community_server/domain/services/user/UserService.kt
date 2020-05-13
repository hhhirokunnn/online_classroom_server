package com.hhhirokunnn.classroom_community_server.domain.services.user

import com.hhhirokunnn.classroom_community_server.app.models.errors.UserNotFoundError
import com.hhhirokunnn.classroom_community_server.app.models.parameters.UserRegisterParameter
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserEntity
import com.hhhirokunnn.classroom_community_server.domain.entities.user.UserRepository
import com.hhhirokunnn.classroom_community_server.domain.utils.MyPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun save(user: UserRegisterParameter): UserEntity {
        val toEntity = UserEntity(
            name = user.name,
            mail = user.mail,
            password = MyPasswordEncoder.run(user.password)
        )
        return userRepository.save(toEntity)
    }

    fun doFindById(id: Long): UserEntity = userRepository.findById(id).orElseThrow { UserNotFoundError() }

    fun findByMailAndPassword(mail: String, password: String): UserEntity? =
        userRepository.findByMail(mail)?.takeIf {
            MyPasswordEncoder.matches(rawPassword = password, encryptedPassword = it.password)}
}
