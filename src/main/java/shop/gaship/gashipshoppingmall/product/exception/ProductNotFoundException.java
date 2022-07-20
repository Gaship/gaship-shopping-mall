package shop.gaship.gashipshoppingmall.product.exception;

/**
 * 제품이 존재하지않을경우 예외 발생
 *
 * @author : 유호철
 * @since 1.0
 */
public class ProductNotFoundException extends RuntimeException {
    private static final String MESSAGE = "제품이 존재하지않습니다.";

    public ProductNotFoundException() {
        super(MESSAGE);
    }
}
