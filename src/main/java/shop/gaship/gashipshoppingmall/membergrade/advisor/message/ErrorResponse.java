package shop.gaship.gashipshoppingmall.membergrade.advisor.message;

/**
 * 예외처리 응답 객체.
 *
 * @author : 김세미
 * @since 1.0
 */
public class ErrorResponse {
    private final String message;

    /**
     * Instantiates a new Error response.
     *
     * @param message 예외 발생시 예외 메시지를 담는 변수 (String)
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
