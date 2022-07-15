package shop.gaship.gashipshoppingmall.membergrade.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.exception
 * fileName       : DefaultMemberGradeIsExist
 * author         : Semi Kim
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14        Semi Kim       최초 생성
 */
public class DefaultMemberGradeIsExist extends RuntimeException {
    private static final String MESSAGE = "기본 회원등급이 이미 존재합니다.";

    public DefaultMemberGradeIsExist() {
        super(MESSAGE);
    }
}
