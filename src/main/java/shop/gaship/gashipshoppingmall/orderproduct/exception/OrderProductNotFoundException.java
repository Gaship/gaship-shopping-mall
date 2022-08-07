package shop.gaship.gashipshoppingmall.orderproduct.exception;

/**
 * 주문상품을 찾지 못하였을 때 발생시키는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class OrderProductNotFoundException extends RuntimeException {
    private static final String MESSAGE = "주문상품을 찾을 수 없습니다.";

    public OrderProductNotFoundException() {
        super(MESSAGE);
    }
}
