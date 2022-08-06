package shop.gaship.gashipshoppingmall.member.exception;

/**
 * 중복된 닉네임에 대한 예외를 발생시 던지는 예외 클래스입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class DuplicatedNicknameException extends RuntimeException {
    public static final String MESSAGE = "중복된 닉네임입니다";

    public DuplicatedNicknameException() {
        super(MESSAGE);
    }
}
