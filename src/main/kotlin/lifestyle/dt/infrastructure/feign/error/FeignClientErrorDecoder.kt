package lifestyle.dt.infrastructure.feign.error

import feign.Response
import feign.codec.ErrorDecoder
import java.lang.Exception

class FeignClientErrorDecoder : ErrorDecoder{

    override fun decode(methodKey: String?, response: Response): Exception {

        when(response.status()){
            401 ->
        }
    }
}