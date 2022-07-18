package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * 사용중인 회원등급 Exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class MemberGradeInUseException extends RuntimeException {
    private static final String MESSAGE = "사용중인 회원등급 입니다.";

    /**
     * Instantiates a new MemberGradeInUseException.
     */
    public MemberGradeInUseException() {
        super(MESSAGE);
    }
}
