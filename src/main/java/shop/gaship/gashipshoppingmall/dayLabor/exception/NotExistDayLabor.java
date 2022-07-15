package shop.gaship.gashipshoppingmall.dayLabor.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.exception
 * fileName       : NotExistDayLabor
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초 생성
 */
public class NotExistDayLabor extends RuntimeException {

    public NotExistDayLabor() {
        super("관련 지역물량이 존재하지않습니다");
    }
}
