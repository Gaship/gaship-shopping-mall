package shop.gaship.gashipshoppingmall.message;

/**
 *
 * 에러 발생 시 응답 바디에 포함할 객체
 *
 * @author : 김보민
 * @since 1.0
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
