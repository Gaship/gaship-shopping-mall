package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 상품문의인 경우, 상품번호가 null 이면 발생하는 예외입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class ProductInquiryHasNullProductNoException extends RuntimeException {
    public static final String MESSAGE = "상품문의에는 상품번호가 필수값입니다.";

    public ProductInquiryHasNullProductNoException() {
        super(MESSAGE);
    }
}
