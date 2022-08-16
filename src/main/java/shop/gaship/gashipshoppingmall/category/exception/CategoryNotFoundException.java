package shop.gaship.gashipshoppingmall.category.exception;

/**
 * 카테고리를 찾을 수 없을 때 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class CategoryNotFoundException extends RuntimeException {
    public static final String MESSAGE = "카테고리를 찾을 수 없습니다.";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }
}
