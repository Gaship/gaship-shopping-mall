package shop.gaship.gashipshoppingmall.repairSchedule.dummy;

import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.CreateScheduleRequestDto;

import java.time.LocalDate;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dummy fileName       :
 * CreateScheduleRequestDtoDummy author         : 유호철 date           : 2022/07/14 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/14       유호철       최초
 * 생성
 */

public class CreateScheduleRequestDtoDummy {
    private CreateScheduleRequestDtoDummy() {

    }

    public static CreateScheduleRequestDto dummy() {
        return new CreateScheduleRequestDto(LocalDate.now(), 1, 10);
    }

}
