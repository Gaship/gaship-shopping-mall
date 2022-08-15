package shop.gaship.gashipshoppingmall.statuscode.exception;

/**
 * 주문 상태 변경시 잘못된 상태값이 입력 될 경우 발생시키는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class InvalidOrderStatusException extends RuntimeException {
    public static final String HEAD_MESSAGE = "해당 주문 상태로는 ";
    public static final String TAIL_MESSAGE = "로 변경이 불가능합니다.";

    public InvalidOrderStatusException(String serviceStatus) {
        super(HEAD_MESSAGE + serviceStatus + TAIL_MESSAGE);
    }
}
