package shop.gaship.gashipshoppingmall.orderproduct.exception;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */
public class OrderProductDetailNoValueException extends RuntimeException {
    public static final String MESSAGE = "주문상품에대한 상세값이 없습니다.";

    public OrderProductDetailNoValueException() {
        super(MESSAGE);
    }
}
