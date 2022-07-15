package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.exception
 * fileName       : CannotDeleteDefaultMemberGrade
 * author         : Semi Kim
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14        Semi Kim       최초 생성
 */
public class CannotDeleteDefaultMemberGrade extends RuntimeException {
    private static final String MESSAGE = "기본 회원등급은 삭제할 수 없습니다.";

    public CannotDeleteDefaultMemberGrade() {
        super(MESSAGE);
    }
}
