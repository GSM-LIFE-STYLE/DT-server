package lifestyle.dt.global.error.exception

import lifestyle.dt.global.error.ErrorCode
import java.lang.RuntimeException

open class BusinessException(val errorCode: ErrorCode): RuntimeException(errorCode.message)