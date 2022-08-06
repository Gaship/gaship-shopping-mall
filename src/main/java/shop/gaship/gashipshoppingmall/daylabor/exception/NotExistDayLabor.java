package shop.gaship.gashipshoppingmall.daylabor.exception;

/**
 * 관련지역물량이 없을경우 예외를 표기해주기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class NotExistDayLabor extends RuntimeException {
    private static final String MESSAGE = "관련 지역물량이 존재하지않습니다";

    public NotExistDayLabor() {
        super(MESSAGE);
    }
}
