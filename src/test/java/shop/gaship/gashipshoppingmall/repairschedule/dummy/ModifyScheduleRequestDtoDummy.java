package shop.gaship.gashipshoppingmall.repairschedule.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.ModifyScheduleRequestDto;

import java.time.LocalDate;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dummy
 * fileName       : ModifyScheduleRequestDtoDummy
 * author         : 유호철
 * date           : 2022/07/14
 * description    : 테스트를 위한 더미 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
public class ModifyScheduleRequestDtoDummy {

    private ModifyScheduleRequestDtoDummy() {

    }

    public static ModifyScheduleRequestDto dummy() {
        ModifyScheduleRequestDto dummy = new ModifyScheduleRequestDto();
        ReflectionTestUtils.setField(dummy, "date", LocalDate.now());
        ReflectionTestUtils.setField(dummy, "labor", 10);
        ReflectionTestUtils.setField(dummy, "localNo", 1);

        return dummy;
    }
}
