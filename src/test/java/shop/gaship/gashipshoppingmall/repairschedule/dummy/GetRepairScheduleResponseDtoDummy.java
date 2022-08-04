package shop.gaship.gashipshoppingmall.repairschedule.dummy;

import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.repairschedule.dto.response.GetRepairScheduleResponseDto;

import java.time.LocalDate;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dummy
 * fileName       : GetRepairScheduleResponseDtoDummy
 * author         : 유호철
 * date           : 2022/07/14
 * description    : test 조회를 위한 dummy
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
public class GetRepairScheduleResponseDtoDummy {
    private GetRepairScheduleResponseDtoDummy() {

    }

    public static GetRepairScheduleResponseDto dummy1() {
        GetRepairScheduleResponseDto dummy = new GetRepairScheduleResponseDto();
        ReflectionTestUtils.setField(dummy, "localName", "마산턱별시");
        ReflectionTestUtils.setField(dummy, "localDate", LocalDate.now());
        ReflectionTestUtils.setField(dummy, "labor", 1);

        return dummy;

    }

    public static GetRepairScheduleResponseDto dummy2() {
        GetRepairScheduleResponseDto dummy = new GetRepairScheduleResponseDto();
        ReflectionTestUtils.setField(dummy, "localName", "서우루특벼르시");
        ReflectionTestUtils.setField(dummy, "localDate", LocalDate.now());
        ReflectionTestUtils.setField(dummy, "labor", 2);

        return dummy;
    }
}
