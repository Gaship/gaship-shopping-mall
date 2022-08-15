package shop.gaship.gashipshoppingmall.category.exception;

/**
 * 카테고리 삭제 시 하위 카테고리가 존재할 때 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class CategoryRemainLowerCategoryException extends RuntimeException {
    public static final String MESSAGE = "해당 카테고리에 속한 하위 카테고리가 있어 삭제할 수 없습니다.";

    public CategoryRemainLowerCategoryException() {
        super(MESSAGE);
    }
}
