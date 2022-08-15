package shop.gaship.gashipshoppingmall.product.exception;

/**
 * 주문 상세 품목을 주문에 등록 한 뒤 재고 수량을 줄일 때, 재고가 부족할 시 일으키는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class NoMoreProductException extends RuntimeException {
    public static final String MESSAGE = "재고가 더 이상 없습니다.";

    public NoMoreProductException() {
        super(MESSAGE);
    }
}
