package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 회원의 권한인 사람이 고객문의요청을 상세문의 요청 메소드에서 했거나 그 반대인 경우에 예외를 발생시키기 위한 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class WrongInquiryApproachException extends RuntimeException {

    public static final String MESSAGE = "잘못된 문의 접근입니다. 상품문의인지 고객문의인지 다시 확인해주세요.";

    public WrongInquiryApproachException() {
        super(MESSAGE);
    }
}
