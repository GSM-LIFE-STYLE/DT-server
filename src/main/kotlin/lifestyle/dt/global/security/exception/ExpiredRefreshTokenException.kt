package lifestyle.dt.global.security.exception

import lifestyle.dt.global.error.ErrorCode
import lifestyle.dt.global.error.exception.DTException

class ExpiredRefreshTokenException: DTException(ErrorCode.EXPIRED_REFRESH_TOKEN)