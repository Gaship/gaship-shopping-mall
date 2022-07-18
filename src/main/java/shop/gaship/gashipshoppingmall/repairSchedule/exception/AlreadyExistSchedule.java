package shop.gaship.gashipshoppingmall.repairSchedule.exception;

/**
 * 이미존재하는 스케줄일경우 사용되는 클래스입니다.
 *
 *
 * @see RuntimeException
 * @author : 유호철
 * @since 1.0
 */
public class AlreadyExistSchedule extends RuntimeException {

    public AlreadyExistSchedule() {
        super("이미 일정이 존재합니다");
    }
}
