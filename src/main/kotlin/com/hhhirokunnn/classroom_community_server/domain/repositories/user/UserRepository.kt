package com.hhhirokunnn.classroom_community_server.domain.entities.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {

    fun save(userEntity: UserEntity): UserEntity
    fun findByMail(mail: String): UserEntity?
}
