package shop.gaship.gashipshoppingmall.tag.exception;

/**
 * 태그명이 중복되었을때 발생하는 에러입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class DuplicatedTagTitleException extends RuntimeException {
    private static final String MESSAGE = "중복된 태그명입니다";

    public DuplicatedTagTitleException() {
        super(MESSAGE);
    }
}
