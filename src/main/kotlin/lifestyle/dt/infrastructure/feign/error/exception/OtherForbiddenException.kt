package lifestyle.dt.infrastructure.feign.error.exception

import lifestyle.dt.global.error.ErrorCode
import lifestyle.dt.global.error.exception.DTException

class OtherForbiddenException : DTException(ErrorCode.OTHER_FORBIDDEN)