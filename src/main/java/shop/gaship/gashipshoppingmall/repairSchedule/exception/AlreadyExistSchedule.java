package shop.gaship.gashipshoppingmall.repairSchedule.exception;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.exception
 * fileName       : AlreadyExistSchedule
 * author         : 유호철
 * date           : 2022/07/14
 * description    : 일정을 생성하려했을때 이미생성되어 있는경우
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */

public class AlreadyExistSchedule extends RuntimeException {

    public AlreadyExistSchedule() {
        super("이미 일정이 존재합니다");
    }
}
