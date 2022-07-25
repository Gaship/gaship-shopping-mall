package shop.gaship.gashipshoppingmall.membertag.exception;

/**
 * @author 최정우
 * @description 태그등록을 할 때 갯수가 5개가 아니면 발생하는 클래스입니다.
 * @since 1.0
 */
public class IllegalTagSelectionException extends RuntimeException {
    public static final String MESSAGE = "태그는 다섯개까지 선택할 수 있습니다";

    public IllegalTagSelectionException() {
        super(MESSAGE);
    }
}
