package shop.gaship.gashipshoppingmall.tag.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.exception
 * fileName       : DuplicatedTagTitleException
 * author         : choijungwoo
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        choijungwoo       최초 생성
 */
public class DuplicatedTagTitleException extends RuntimeException {
    /**
     * The constant MESSAGE.
     */
    public static final String MESSAGE = "중복된 태그명입니다";

    /**
     * Instantiates a new Duplicated tag title exception.
     */
    public DuplicatedTagTitleException() {
        super(MESSAGE);
    }
}
