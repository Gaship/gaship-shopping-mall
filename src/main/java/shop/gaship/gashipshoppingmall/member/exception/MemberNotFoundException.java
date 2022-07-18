package shop.gaship.gashipshoppingmall.member.exception;

/**
 * 멤버조회시에 에러가 발생했을 때 발생하는 에러입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class MemberNotFoundException extends RuntimeException{
    public static final String MESSAGE = "해당 멤버를 찾을 수 없습니다";
    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
