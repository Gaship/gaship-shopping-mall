package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * 존재하지 않는 회원 등급 Exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class MemberGradeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 회원 등급을 찾을 수 없습니다.";

    /**
     * Instantiates a new MemberGradeNotFoundException.
     */
    public MemberGradeNotFoundException() {
        super(MESSAGE);
    }
}
