package shop.gaship.gashipshoppingmall.tag.exception;

/**
 * 태그조회 요청에 실패하면 발생하는 에러입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public class TagNotFoundException extends RuntimeException {
    public static final String MESSAGE = "해당 태그를 찾을 수 없습니다";

    public TagNotFoundException() {
        super(MESSAGE);
    }
}
