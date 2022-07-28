package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 답변 등록이 이미 완료되었는데 재차 답변요청을 할시에 발생시킬 예외클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class AlreadyCompleteInquiryAnswerException extends RuntimeException {

    public static final String MESSAGE = "해당 문의에 답변이 이미 등록되어 있습니다.";

    public AlreadyCompleteInquiryAnswerException() {
        super(MESSAGE);
    }
}
