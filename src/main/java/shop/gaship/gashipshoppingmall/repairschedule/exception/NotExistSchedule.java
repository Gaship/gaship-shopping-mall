package shop.gaship.gashipshoppingmall.repairschedule.exception;

/**
 * 스케줄이 없을경우 사용되는 클래스입니다.
 *
 *
 * @see RuntimeException
 * @author : 유호철
 * @since 1.0
 */
public class NotExistSchedule extends RuntimeException {

    public NotExistSchedule() {
        super("일정이 존재하지 않습니다");
    }
}
