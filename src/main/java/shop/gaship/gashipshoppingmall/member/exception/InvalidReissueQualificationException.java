package shop.gaship.gashipshoppingmall.member.exception;

/**
 * 유효하지 않은 자격으로 비밀번호 재발급을 요청시 발생시킬 예외 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class InvalidReissueQualificationException extends RuntimeException {
    private static final String ERROR_MESSAGE = "유효하지 않은 접근으로 인해 요청을 취하합니다.";
    
    public InvalidReissueQualificationException() {
        super(ERROR_MESSAGE);
    }
}
