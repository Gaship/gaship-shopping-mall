package shop.gaship.gashipshoppingmall.member.exception;


/**
 * 회원의 정보를 찾지 못하였을 때 발생하는 예외입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException() {
        super("찿고있는 회원의 정보가 존재하지않습니다.");
    }
}
