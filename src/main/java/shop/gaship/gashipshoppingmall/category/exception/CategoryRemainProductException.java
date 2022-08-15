package shop.gaship.gashipshoppingmall.category.exception;

/**
 * 카테고리를 삭제 시 속한 상품이 있을 때 던질 예외입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public class CategoryRemainProductException extends RuntimeException {
    public static final String MESSAGE = "카테고리에 속한 상품이 있어 삭제할 수 없습니다.";

    public CategoryRemainProductException() {
        super(MESSAGE);
    }
}
