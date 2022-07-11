package shop.gaship.gashipshoppingmall.category.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.exception
 * fileName       : CategoryRemainProductException
 * author         : 김보민
 * date           : 2022-07-11
 * description    : 속한 상품이 있는 카테고리를 제거하려할 때 던질 예외
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
public class CategoryRemainProductException extends RuntimeException {
    public CategoryRemainProductException() {
        super("카테고리에 속한 상품이 있어 삭제할 수 없습니다.");
    }
}
