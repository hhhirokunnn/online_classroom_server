package com.hhhirokunnn.classroom_community_server.app.controllers.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hello")
class HelloController {
    @GetMapping("")
    fun index(): Test {
        return Test(1, "")
    }
}

data class Test (
    val id: Int,
    val name: String
)
