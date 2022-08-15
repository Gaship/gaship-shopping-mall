package shop.gaship.gashipshoppingmall.orderproduct.exception;

/**
 * 주문취소이력번호가 일치하지 않는경우, 주문취소복구가 불가능할 때 발생시키는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class InvalidOrderCancellationHistoryNo extends RuntimeException {
    public static final String MESSAGE = "일치하지않는 취소이력번호입니다.";

    public InvalidOrderCancellationHistoryNo() {
        super(MESSAGE);
    }
}
