package shop.gaship.gashipshoppingmall.member.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.exception
 * fileName       : MemberCouldNotFoundException
 * author         : choijungwoo
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        choijungwoo       최초 생성
 */
public class MemberNotFoundException extends RuntimeException{
    public static final String MESSAGE = "해당 멤버를 찾을 수 없습니다";
    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
