package lifestyle.dt.domain.auth.exception

import lifestyle.dt.global.error.ErrorCode
import lifestyle.dt.global.error.exception.DTException

class UserNotFoundException: DTException(ErrorCode.NOT_FOUND_USER)