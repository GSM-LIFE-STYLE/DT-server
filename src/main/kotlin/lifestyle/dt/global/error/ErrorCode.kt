package lifestyle.dt.global.error

enum class ErrorCode(
    val message: String,
    val code: Int
) {

    BAD_REQUEST("잘못된 요청", 400),
    PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다.", 400),
    NOT_FOUND_USER("유저가 존재하지 않습니다.",404),

    UNAUTHORIZED("권한 없음", 401),
    EXPIRED_TOKEN("토큰 만료", 401),
    INVALID_TOKEN("토큰 변질", 401),
    EXPIRED_REFRESH_TOKEN("리프레시 토큰이 만료되었습니다", 401),

    FORBIDDEN("금지된 요청입니다.", 403),

    DUPLICATE_EMAIL("중복되는 이메일입니다.", 409),

    INTERNAL_SERVER_ERROR("서버 내부 에러", 500),

    OTHER_BAD_REQUEST("other server bad request", 400),
    OTHER_UNAUTHORIZED("other server unauthorized", 401),
    OTHER_FORBIDDEN( "other server forbidden", 403),
    OTHER_EXPIRED_TOKEN("other server expired token", 419),
}