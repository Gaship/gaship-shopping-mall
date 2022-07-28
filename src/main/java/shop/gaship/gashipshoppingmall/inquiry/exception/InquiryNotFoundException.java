package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 상품문의를 조회시 상품문의가 없을때 발생할 예외클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
public class InquiryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "문의를 찾을 수 없습니다.";

    public InquiryNotFoundException() {
        super(MESSAGE);
    }
}
