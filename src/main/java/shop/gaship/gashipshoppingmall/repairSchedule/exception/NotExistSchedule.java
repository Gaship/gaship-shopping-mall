package shop.gaship.gashipshoppingmall.repairSchedule.exception;

/**
 *packageName    : shop.gaship.gashipshoppingmall.repairSchedule.exception
 * fileName       : NotExistSchedule
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
public class NotExistSchedule extends RuntimeException {

    public NotExistSchedule() {
        super("일정이 존재하지 않습니다");
    }
}
