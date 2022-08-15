package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * 기본 회원 등급 삭제 불가 Exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class CannotDeleteDefaultMemberGrade extends RuntimeException {
    public static final String MESSAGE = "기본 회원등급은 삭제할 수 없습니다.";

    /**
     * Instantiates a new CannotDeleteDefaultMemberGrade Exception.
     */
    public CannotDeleteDefaultMemberGrade() {
        super(MESSAGE);
    }
}
