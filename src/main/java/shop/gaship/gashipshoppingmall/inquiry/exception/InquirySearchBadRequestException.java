package shop.gaship.gashipshoppingmall.inquiry.exception;

/**
 * 문의리스트 검색시 요청정보가 잘못온경우에 발생시킬 예외 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class InquirySearchBadRequestException extends RuntimeException {
    public static final String MESSAGE = "잘못된 문의 조회 요청입니다.";

    public InquirySearchBadRequestException() {
        super(MESSAGE);
    }
}
