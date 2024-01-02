package registered.project.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import registered.project.api.entities.User


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration{

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http.csrf { csfr -> csfr.disable() }.authorizeHttpRequests{
            auto -> auto.requestMatchers("/h2-console/**").permitAll().anyRequest().permitAll()}.headers { fr -> fr.frameOptions{f -> f.sameOrigin()}}
            .sessionManagement{session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}.build()
        }


    }

