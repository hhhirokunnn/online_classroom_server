package com.hhhirokunnn.classroom_community_server.configs

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class MySecurityConfig: WebSecurityConfigurerAdapter() {

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.cors().and().csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
            .exceptionHandling().authenticationEntryPoint(JwtAuthenticationEntryPoint()).and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/api/users").permitAll()
            .antMatchers(HttpMethod.POST, "/api/users").permitAll()
            .antMatchers(HttpMethod.GET, "/api/articles").permitAll()
            .antMatchers(HttpMethod.GET, "/api/articles/{articleId}").permitAll()
            .antMatchers(HttpMethod.POST, "/api/articles").permitAll()
            .antMatchers(HttpMethod.GET, "/api/markedArticles").permitAll()
            .antMatchers(HttpMethod.POST, "/api/markedArticles").permitAll()
            .antMatchers(HttpMethod.GET, "/api/steps").permitAll()
            .antMatchers(HttpMethod.POST, "/api/steps").permitAll()
            .antMatchers(HttpMethod.GET, "/api/materials").permitAll()
            .antMatchers(HttpMethod.POST, "/api/materials").permitAll()
            .antMatchers(HttpMethod.GET, "/api/comments").permitAll()
            .antMatchers(HttpMethod.POST, "/api/comments").permitAll()
            .antMatchers(HttpMethod.POST, "/api/login").permitAll()
            .antMatchers(HttpMethod.DELETE, "/api/logout").permitAll()
            .anyRequest().authenticated()
            .and()
//            .addFilterAfter(CustomFilter(), UsernamePasswordAuthenticationFilter().javaClass)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }
}
