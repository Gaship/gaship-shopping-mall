package shop.gaship.gashipshoppingmall.category.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.exception
 * fileName       : CategoryRemainLowerCategory
 * author         : 김보민
 * date           : 2022-07-11
 * description    : 하위 카테고리를 가지는 카테고리를 삭제하려할 때 던질 예외
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
public class CategoryRemainLowerCategory extends RuntimeException {
    public CategoryRemainLowerCategory() {
        super("해당 카테고리에 속한 하위 카테고리가 있어 삭제할 수 없습니다.");
    }
}
