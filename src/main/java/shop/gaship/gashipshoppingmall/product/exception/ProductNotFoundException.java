package shop.gaship.gashipshoppingmall.product.exception;

/**
 * 제품이 존재하지않을경우 발생하는 예외 입니다.
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
