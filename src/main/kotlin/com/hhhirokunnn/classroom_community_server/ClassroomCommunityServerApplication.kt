package com.hhhirokunnn.classroom_community_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
class ClassroomCommunityServerApplication

fun main(args: Array<String>) {
	runApplication<ClassroomCommunityServerApplication>(*args)
}

