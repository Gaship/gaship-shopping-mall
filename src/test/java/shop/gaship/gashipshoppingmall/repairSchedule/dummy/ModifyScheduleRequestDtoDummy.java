package shop.gaship.gashipshoppingmall.repairSchedule.dummy;

import java.time.LocalDate;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.ModifyScheduleRequestDto;

/**
 *packageName    : shop.gaship.gashipshoppingmall.repairSchedule.dummy
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

     private ModifyScheduleRequestDtoDummy(){

     }

     public static ModifyScheduleRequestDto dummy(){
         return ModifyScheduleRequestDto.builder()
             .date(LocalDate.now())
             .labor(10)
             .localNo(1)
             .build();
    }
}
