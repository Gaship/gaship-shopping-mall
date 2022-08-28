package shop.gaship.gashipshoppingmall.error;

/**
 * @author : 최겸준
 * @since 1.0
 */
public class MemberForbiddenException extends RuntimeException {

    public static final String MESSAGE = "잘못된 접근, 유저 본인이 아닙니다.";

    public MemberForbiddenException() {
        super(MESSAGE);
    }
}
