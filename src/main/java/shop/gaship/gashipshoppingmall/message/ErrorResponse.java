package shop.gaship.gashipshoppingmall.message;

/**
 * 에러 발생 시 응답 바디에 포함할 객체입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class ErrorResponse {
    private String message;

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
