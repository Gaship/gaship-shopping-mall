package shop.gaship.gashipshoppingmall.productreview.exception;

/**
 * 상품평을 찾을 수 없을 때 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class ProductReviewNotFoundException extends RuntimeException {
    public static final String MESSAGE = "해당 상품평을 찾을 수 없습니다.";

    public ProductReviewNotFoundException() {
        super(MESSAGE);
    }
}
