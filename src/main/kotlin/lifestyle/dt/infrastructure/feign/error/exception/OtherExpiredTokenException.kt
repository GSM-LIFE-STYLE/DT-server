package lifestyle.dt.infrastructure.feign.error.exception

import lifestyle.dt.global.error.ErrorCode
import lifestyle.dt.global.error.exception.DTException

class OtherExpiredTokenException : DTException(ErrorCode.OTHER_EXPIRED_TOKEN)