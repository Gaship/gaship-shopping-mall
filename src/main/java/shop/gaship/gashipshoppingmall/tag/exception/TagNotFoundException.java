package shop.gaship.gashipshoppingmall.tag.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.exception
 * fileName       : TagNotFoundException
 * author         : choijungwoo
 * date           : 2022/07/17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/17        choijungwoo       최초 생성
 */
public class TagNotFoundException extends RuntimeException{
    public static final String MESSAGE = "해당 태그를 찾을 수 없습니다";

    public TagNotFoundException() {
        super(MESSAGE);
    }
}
