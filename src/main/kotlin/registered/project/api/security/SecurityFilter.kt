package registered.project.api.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import registered.project.api.entities.User
import registered.project.api.repositories.UserRepository


@Component
class SecurityFilter(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
): OncePerRequestFilter() {


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = this.recoverToken(request)

        if(token!=null){
            val email = tokenService.validateToken(token)
            val user: User? = userRepository.findByEmailCustom(email)

            if(user!=null){
                val authentication= UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    fun recoverToken(request:HttpServletRequest ):String?{
        val authHeader = request.getHeader("Authorization") ?: return null
        return authHeader.replace("Bearer ", "")
    }

}