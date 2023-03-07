package lifestyle.dt.global.error

enum class ErrorCode(
    val message: String,
    val code: Int
) {
    BAD_REQUEST("잘못된 요청", 400),
    INTERNAL_SERVER_ERROR("서버 내부 에러", 500)
}