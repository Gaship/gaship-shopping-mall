package shop.gaship.gashipshoppingmall.category.exception;

/**
 *
 * 카테고리를 찾을 수 없을 때 던질 예외
 *
 * @author : 김보민
 * @since 1.0
 */
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
        super("카테고리를 찾을 수 없습니다.");
    }
}
