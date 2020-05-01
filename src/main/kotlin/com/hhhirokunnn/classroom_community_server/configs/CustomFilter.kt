package com.hhhirokunnn.classroom_community_server.configs

import com.hhhirokunnn.classroom_community_server.app.models.errors.AuthenticateError
import com.hhhirokunnn.classroom_community_server.app.models.errors.NoBearerError
import com.hhhirokunnn.classroom_community_server.app.models.errors.NoHeaderParameterError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletContext
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

// @deprecated
//class CustomFilter : GenericFilterBean() {
//
//    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
//        val req = request.let {
//            val httpRequest = it as HttpServletRequest
//            if(httpRequest.requestURI.toString() != "/api/login" && httpRequest.requestURI.toString() != "/error") {
//                httpRequest.getHeader("authorization")
//                    ?: throw NoHeaderParameterError(error = AuthenticateError())
//            }
//            httpRequest
//        }
//
//        chain?.doFilter(req, response)
//    }
//}
