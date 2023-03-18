package lifestyle.dt.global.error.exception

import lifestyle.dt.global.error.ErrorCode
import java.lang.RuntimeException

open class DTException(val errorCode: ErrorCode) : RuntimeException(errorCode.message){
}