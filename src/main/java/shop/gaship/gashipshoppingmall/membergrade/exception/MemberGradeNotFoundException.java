package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.exception
 * fileName       : MemberGradeNotFoundException
 * author         : Semi Kim
 * date           : 2022/07/10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/10        Semi Kim       최초 생성
 */
public class MemberGradeNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당 회원 등급을 찾을 수 없습니다.";

    public MemberGradeNotFoundException() {
        super(MESSAGE);
    }
}
