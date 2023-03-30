package lifestyle.dt.infrastructure.feign.error

import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import lifestyle.dt.infrastructure.feign.error.exception.OtherBadRequestException
import lifestyle.dt.infrastructure.feign.error.exception.OtherExpiredTokenException
import lifestyle.dt.infrastructure.feign.error.exception.OtherForbiddenException
import lifestyle.dt.infrastructure.feign.error.exception.OtherUnAuthorizedException

class FeignClientErrorDecoder : ErrorDecoder{

    override fun decode(methodKey: String?, response: Response): FeignException =
        when(response.status()) {
            in 400 .. 499 -> when (response.status()) {
                401 -> throw OtherUnAuthorizedException()
                403 -> throw OtherForbiddenException()
                419 -> throw OtherExpiredTokenException()
                else -> throw OtherBadRequestException()
            }
            else -> FeignException.errorStatus(methodKey, response)
        }
}