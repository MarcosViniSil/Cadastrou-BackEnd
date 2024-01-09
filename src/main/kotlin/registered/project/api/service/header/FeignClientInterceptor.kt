package registered.project.api.service.header

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder

import org.springframework.web.context.request.ServletRequestAttributes


@Component
class FeignClientInterceptor : RequestInterceptor {

    private val AUTHORIZATION_HEADER: String = "Authorization"

    fun getBearerTokenHeader(): String {
        val token:String= (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request.getHeader("Authorization")
        return token.replace("Bearer ", "")
    }

    override fun apply(template: RequestTemplate?) {

        template?.header(AUTHORIZATION_HEADER, getBearerTokenHeader());
    }
}