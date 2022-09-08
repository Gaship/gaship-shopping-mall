package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 고객문의인 경우, 상품번호가 존재하면 발생하는 예외입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class CustomerInquiryHasProductNoException extends RuntimeException {
    public static final String MESSAGE = "고객문의에는 상품번호가 들어가면 안됩니다.";

    public CustomerInquiryHasProductNoException() {
        super(MESSAGE);
    }
}
