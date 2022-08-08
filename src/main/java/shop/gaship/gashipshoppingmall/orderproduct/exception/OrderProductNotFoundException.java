package shop.gaship.gashipshoppingmall.orderproduct.exception;

/**
 * 주문상품을 찾을 수 없을 때 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class OrderProductNotFoundException extends RuntimeException {
    public static final String MESSAGE = "해당 주문상품을 찾을 수 없습니다.";

    public OrderProductNotFoundException() {
        super(MESSAGE);
    }
}
