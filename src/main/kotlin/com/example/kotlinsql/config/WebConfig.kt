package com.example.kotlinsql.config

import com.example.kotlinsql.security.JwtFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebConfig(val jwtFilter: JwtFilter) {

    @Bean
    fun filterRegistration(): FilterRegistrationBean<JwtFilter> {
        val registrationBean = FilterRegistrationBean(jwtFilter)
        registrationBean.addUrlPatterns(
            "/usuarios/*",
            "/roles/*",
            "/usuario-roles/*",
            "/estudiantes/*",
            "/asistencias/*",
            "/asistencia-estudiantes/*",
            "/calificaciones/*",
            "/calificacion-estudiantes/*",
            "/certificados/*",
            "/auditorias/*"
        )
        return registrationBean
    }
}

