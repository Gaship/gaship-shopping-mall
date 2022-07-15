package shop.gaship.gashipshoppingmall.repairSchedule.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;

import java.time.LocalDate;
import java.util.List;

/**
 * QueryDsl 을 사용하기위한 인터페이스 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

public interface RepairScheduleRepositoryCustom {

    /**
     * 일자를 통해 모든 스케줄을 조회하기위한 메서드입니다.
     *
     *
     * @param date 조회하기위한 일자가입력 됩니다.
     * @return list 조회된 스케줄정보가 반환됩니다.
     * @author 유호철
     */
    List<GetRepairScheduleResponseDto> findAllByDate(LocalDate date);

    /**
     * 페이지형태의 데이터로 모든 스케줄정보를 조회하기위한 메서드입니다.
     *
     *
     * @param pageable 페이지정보들이 담겨있습니다.
     * @return page 스케줄정보가 페이지형태로 반환됩니다.
     * @author 유호철
     */
    Page<GetRepairScheduleResponseDto> findAllSortDate(Pageable pageable);

}
