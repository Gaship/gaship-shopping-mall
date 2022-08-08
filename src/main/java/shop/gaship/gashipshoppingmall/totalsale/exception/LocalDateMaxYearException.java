package shop.gaship.gashipshoppingmall.totalsale.exception;

/**
 * 최대 1년의 값을 넘지못하게 하기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public class LocalDateMaxYearException extends RuntimeException {
    private static final String MESSAGE = "검색할 날짜는 1년을 넘을수 없습니다.";

    public LocalDateMaxYearException() {
        super(MESSAGE);
    }
}
