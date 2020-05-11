//package com.hhhirokunnn.classroom_community_server.configs
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.web.multipart.commons.CommonsMultipartResolver
//import javax.servlet.http.HttpServletRequest
//
//@Configuration
//class MultipartFileResolverConfig {
//
//    @Bean
//    fun multipartResolver(): CommonsMultipartResolverMine = CommonsMultipartResolverMine()
//
//    class CommonsMultipartResolverMine: CommonsMultipartResolver() {
//
//        override fun isMultipart(request: HttpServletRequest): Boolean {
//            val header = request.getHeader("Content-Type") ?: return false
//
//            return header.contains("multipart/form-data")
//        }
//    }
//}