package shop.gaship.gashipshoppingmall.orderproduct.exception;

/**
 * 주문 상세품이 비어있을 때 발생기키는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class OrderProductEmptyException extends RuntimeException {
    public static final String MESSAGE = "주문 제품이 비어있습니다.";

    public OrderProductEmptyException() {
        super(MESSAGE);
    }
}
