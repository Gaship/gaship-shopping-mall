package shop.gaship.gashipshoppingmall.repairSchedule.dummy;

import java.time.LocalDate;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;

/**
 *packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dummy
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
    private GetRepairScheduleResponseDtoDummy(){

    }

    public static GetRepairScheduleResponseDto dummy1(){
        return GetRepairScheduleResponseDto
            .builder()
            .labor(1)
            .localDate(LocalDate.now())
            .localName("마산턱별시")
            .build();
    }

    public static GetRepairScheduleResponseDto dummy2(){
        return GetRepairScheduleResponseDto
            .builder()
            .labor(2)
            .localDate(LocalDate.now())
            .localName("서우루특벼르시")
            .build();
    }
}
