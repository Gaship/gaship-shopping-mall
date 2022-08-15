package shop.gaship.gashipshoppingmall.order.exception;

/**
 * 주문 정보가 존재하지 않을 때 발생시키는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class OrderNotFoundException extends RuntimeException {
    public static final String MESSAGE = "일치하는 주문정보가 존재하지 않습니다.";

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
