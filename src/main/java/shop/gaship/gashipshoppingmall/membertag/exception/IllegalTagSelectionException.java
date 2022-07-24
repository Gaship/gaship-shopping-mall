package shop.gaship.gashipshoppingmall.membertag.exception;

/**
 * 등록하려는 태그의 갯수가 5개가 아니면 발생하는 에외입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class IllegalTagSelectionException extends RuntimeException {
    public static final String MESSAGE = "태그는 다섯개까지 선택할 수 있습니다";

    public IllegalTagSelectionException() {
        super(MESSAGE);
    }
}
