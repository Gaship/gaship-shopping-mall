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
    public DefaultMemberGradeIsExist(String message) {
        super(message);
    }
}
