package lifestyle.dt.domain.auth.exception

import lifestyle.dt.global.error.ErrorCode
import lifestyle.dt.global.error.exception.DTException

class DuplicateEmailException: DTException(ErrorCode.DUPLICATE_EMAIL)