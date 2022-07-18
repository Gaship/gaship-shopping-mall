package shop.gaship.gashipshoppingmall.member.exception;

/**
 * @author 최정우
 * @since 1.0
 */
public class StatusCodeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 상태 코드를 찾을 수 없습니다.";

    public StatusCodeNotFoundException() {
        super(MESSAGE);
    }
}
