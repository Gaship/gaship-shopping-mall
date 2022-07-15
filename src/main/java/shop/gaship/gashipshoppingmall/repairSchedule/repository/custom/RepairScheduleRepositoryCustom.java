package shop.gaship.gashipshoppingmall.repairSchedule.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

/**
 * packageName  : shop.gaship.gashipshoppingmall.repairSechedule.repository.custom
 * fileName     : RepairSecheduleRepositoryCustom
 * author       : 유호철
 * date         : 2022/07/13
 * description  :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초생성
 */

public interface RepairScheduleRepositoryCustom {
    List<GetRepairScheduleResponseDto> findAllByDate(LocalDate date);

    Page<GetRepairScheduleResponseDto> findAllSortDate(Pageable pageable);

}
