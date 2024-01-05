package registered.project.api.security


import registered.project.api.entities.User
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value


@Service
class TokenService(

) {
    @Value("\${api-jwt-secret-key}")
    private lateinit var secretKey: String
    fun generatedToken(user: User): String {
        try {
            var algorithm: Algorithm = Algorithm.HMAC256(secretKey)
            var token: String = JWT.create()
                .withIssuer("auth")
                .withSubject(user.email)
                .withExpiresAt(this.getExpirationDate())
                .sign(algorithm);
            return token
        } catch (exception: JWTCreationException) {
            throw RuntimeException("ERROR WHILE GENERATING TOKEN", exception)
        }
    }

    fun validateToken(token: String): String {
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(secretKey)

            return JWT.require(algorithm)
                .withIssuer("auth")
                .build()
                .verify(token)
                .getSubject();
        } catch (exception: JWTVerificationException) {
            return ""
        }
    }

    fun getExpirationDate(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}