package registered.project.api.service.header

import org.springframework.stereotype.Service

@Service
class RecoverToken(
    private val feignClientInterceptor: FeignClientInterceptor
) {

    fun getToken():String{
        return feignClientInterceptor.getBearerTokenHeader()
    }
}