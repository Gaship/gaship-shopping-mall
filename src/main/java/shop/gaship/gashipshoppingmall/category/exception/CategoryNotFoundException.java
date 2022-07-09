package shop.gaship.gashipshoppingmall.category.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.exception
 * fileName       : CategoryNotFoundException
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리를 찾을 수 없을 때 던질 예외
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-09        김보민       최초 생성
 */
public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException() {
        super("카테고리를 찾을 수 없습니다.");
    }
}
