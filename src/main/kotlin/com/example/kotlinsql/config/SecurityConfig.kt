package com.example.kotlinsql.config

import com.example.kotlinsql.security.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtFilter: JwtFilter): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/auth/**").permitAll()
                    .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/**"
                    ).permitAll()
                    .requestMatchers("/usuarios/**").hasAnyRole("ADMINISTRATIVO", "ROOT")
                    .requestMatchers("/roles/**", "/usuario-roles/**").hasAnyRole("ADMINISTRATIVO", "ROOT")
                    .requestMatchers("/estudiantes/**").hasAnyRole("SECRETARIO", "ADMINISTRATIVO", "ROOT")
                    .requestMatchers("/asistencias/**", "/asistencia-estudiantes/**").hasAnyRole("INSTRUCTOR", "ROOT")
                    .requestMatchers("/calificaciones/**", "/calificacion-estudiantes/**").hasAnyRole("INSTRUCTOR", "ROOT")
                    .requestMatchers("/certificados/**").hasAnyRole("ADMINISTRATIVO", "ROOT")
                    .requestMatchers("/auditorias/**").hasAnyRole("ADMINISTRATIVO", "ROOT")
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}
