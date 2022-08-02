package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 답변 등록 또는 수정시에 요청문의 번호와 행위하려는 문의번호가 다를경우 발생시킬 예외 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class DifferentInquiryException extends RuntimeException {

    public static final String MESSAGE = "요청한 문의와 다릅니다.";

    public DifferentInquiryException() {
        super(MESSAGE);
    }
}
