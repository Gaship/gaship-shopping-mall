package shop.gaship.gashipshoppingmall.message;

/**
 *
 * 에러나 예외 발생 시 응답하는 response body 타입
 *
 * @author : 김민수
 * @since 1.0
 */
public class ErrorResponse {
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * 에러메세지를 반환합니다.
     *
     * @return 메세지 문자열을 반환합니다.
     */
    public String getMessage() {
        return message;
    }
}
