package com.hhhirokunnn.classroom_community_server.app.controllers.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/healthCheck")
class HelloController {

    @GetMapping("")
    fun index(): String {
        return "alive"
    }
}
