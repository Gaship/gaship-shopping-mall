package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.exception
 * fileName       : MemberGradeInUseException
 * author         : Semi Kim
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        Semi Kim       최초 생성
 */
public class MemberGradeInUseException extends RuntimeException {
    private static final String MESSAGE = "사용중인 회원등급 입니다.";

    public MemberGradeInUseException() {
        super(MESSAGE);
    }
}
