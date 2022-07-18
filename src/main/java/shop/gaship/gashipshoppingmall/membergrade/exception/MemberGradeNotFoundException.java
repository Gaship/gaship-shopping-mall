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
    public MemberGradeNotFoundException() {
        super("찾는 회원등급의 결과가 존재하지 않습니다.");
    }
}
