package registered.project.api.service

import org.springframework.stereotype.Service
import registered.project.api.service.header.FeignClientInterceptor

@Service
class RecoverToken(
    private val feignClientInterceptor: FeignClientInterceptor
) {

    fun getToken():String{
        return feignClientInterceptor.getBearerTokenHeader()
    }
}