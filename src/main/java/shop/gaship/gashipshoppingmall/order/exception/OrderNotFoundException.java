package shop.gaship.gashipshoppingmall.order.exception;

/**
 * 주문이 없을경우 처리하기위한 에러입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class OrderNotFoundException extends RuntimeException {

    public static final String MESSAGE = "주문을 찾을수 없습니다.";

    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
