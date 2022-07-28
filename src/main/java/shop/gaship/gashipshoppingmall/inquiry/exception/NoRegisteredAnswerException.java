package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 문의 답변 수정시에 문의답변이 달린적이 없을때 발생하는 예외입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class NoRegisteredAnswerException extends RuntimeException {
    public static final String MESSAGE = "등록되지 않은 답변입니다. 답변을 먼저 등록해주세요.";

    public NoRegisteredAnswerException() {
        super(MESSAGE);
    }
}
