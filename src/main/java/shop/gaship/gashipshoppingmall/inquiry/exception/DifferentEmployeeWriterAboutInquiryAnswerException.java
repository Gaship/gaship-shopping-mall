package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 요청한 직원번호와 작성된 문의답변 작성자가 다를때 발생시키는 예외클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class DifferentEmployeeWriterAboutInquiryAnswerException extends RuntimeException {

    public static final String MESSAGE = "원본작성자와 다른 직원입니다.";

    public DifferentEmployeeWriterAboutInquiryAnswerException() {
        super(MESSAGE);
    }
}
