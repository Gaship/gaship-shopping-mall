package shop.gaship.gashipshoppingmall.repairSchedule.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.SchedulePageRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.service
 * fileName       : RepairSecheduleService
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초생성
 */
public interface RepairScheduleService {

    void registerSchedule(CreateScheduleRequestDto dto);

    void modifySchedule(ModifyScheduleRequestDto modify);

    List<GetRepairScheduleResponseDto> findScheduleByDate(LocalDate now);

    Page<GetRepairScheduleResponseDto> getAllSchedule(SchedulePageRequestDto request);
}
