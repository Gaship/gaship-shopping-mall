package shop.gaship.gashipshoppingmall.product.exception;

/**
 * 상품을 찾을 수 없을 때 던질 예외 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class ProductNotFoundException extends RuntimeException{
    private static final String MESSAGE = "상품을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
