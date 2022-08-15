package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * 이미 존재하는 기본 회원 등급 Exception.
 *
 * @author : 김세미
 * @since 1.0
 */
public class DefaultMemberGradeIsExist extends RuntimeException {
    public static final String MESSAGE = "기본 회원등급이 이미 존재합니다.";

    /**
     * Instantiates a new DefaultMemberGradeIsExist Exception.
     */
    public DefaultMemberGradeIsExist() {
        super(MESSAGE);
    }
}
