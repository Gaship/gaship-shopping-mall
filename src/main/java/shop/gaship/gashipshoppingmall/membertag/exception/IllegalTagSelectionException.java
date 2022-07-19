package shop.gaship.gashipshoppingmall.membertag.exception;

/**
 * @author 최정우
 * @since 1.0
 */
public class IllegalTagSelectionException extends RuntimeException {
    public static final String MESSAGE = "태그는 다섯개까지 선택할 수 있습니다";

    public IllegalTagSelectionException() {
        super(MESSAGE);
    }
}
