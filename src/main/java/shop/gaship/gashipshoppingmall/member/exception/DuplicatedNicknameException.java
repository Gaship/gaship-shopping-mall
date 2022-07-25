package shop.gaship.gashipshoppingmall.member.exception;

/**
 * @author 최정우
 * @since 1.0
 */

public class DuplicatedNicknameException extends RuntimeException {
    public static final String MESSAGE = "중복된 닉네임입니다";
    public DuplicatedNicknameException() {
        super(MESSAGE);
    }
}
