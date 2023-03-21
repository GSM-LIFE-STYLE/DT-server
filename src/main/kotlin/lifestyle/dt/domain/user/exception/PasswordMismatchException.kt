package lifestyle.dt.domain.user.exception

import lifestyle.dt.global.error.ErrorCode
import lifestyle.dt.global.error.exception.DTException

class PasswordMismatchException: DTException(ErrorCode.PASSWORD_MISMATCH)