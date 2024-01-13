package registered.project.api.security

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
class SecurityConfigurations(
    private val securityFilter: SecurityFilter
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        return http.
            csrf { csfr -> csfr.disable() }
                .sessionManagement{session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
                .authorizeHttpRequests{
                auto -> auto.requestMatchers("/h2-console/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/User/Register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/User/Login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/User/AdmRegister").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/User/UpdateUser").permitAll()
                            .requestMatchers(HttpMethod.PUT, "/User/UpdateAdm").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/User/delete/{id}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/User/List/{offset}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/User/List/Delete/{offset}").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/User/Request/Delete").permitAll()
                            .requestMatchers(HttpMethod.POST, "/Card/Register").permitAll()
                            .requestMatchers(HttpMethod.GET, "/Card/{offset}").permitAll()
                            .requestMatchers(HttpMethod.DELETE, "/Card/delete/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "Card/Expired/{offset}").permitAll()
                            .requestMatchers(HttpMethod.GET, "User/Validate/Email/{email}").permitAll()
                            .requestMatchers(HttpMethod.POST, "User/Validate/Code").permitAll()
                            .requestMatchers(HttpMethod.GET, "User/Alert").permitAll()}
            .headers { fr -> fr.frameOptions{f -> f.sameOrigin()}}
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }
    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


}