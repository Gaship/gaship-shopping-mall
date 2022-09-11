package shop.gaship.gashipshoppingmall.aspact.exception;

/**
 * 잘못된 접근으로 ID값(회원번호)이 맞지않을 때 발생하는 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class InvalidIdException extends RuntimeException{
    public static final String MESSAGE = "올바른 ID가 아닙니다.";

    public InvalidIdException() {
        super(MESSAGE);
    }
}
