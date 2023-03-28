package lifestyle.dt.infrastructure.feign.error.exception

import lifestyle.dt.global.error.ErrorCode
import lifestyle.dt.global.error.exception.DTException

class OtherBadRequestException : DTException(ErrorCode.OTHER_BAD_REQUEST)