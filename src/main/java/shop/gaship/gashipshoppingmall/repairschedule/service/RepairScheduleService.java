package shop.gaship.gashipshoppingmall.repairschedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.RepairScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.response.GetRepairScheduleResponseDto;

/**
 * 수리스케줄을 서비스레이어에서 사용하기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface RepairScheduleService {

    /**
     * 스케줄을 생성하기위한 메서드입니다.
     *
     * @param dto 생성할 스케줄에대한 정보들이 담겨있습니다.
     * @author 유호철
     */
    void addRepairSchedule(CreateScheduleRequestDto dto);

    /**
     * 스케줄의 수정을위한 메서드입니다.
     *
     * @param modify 수정할 스케줄에대한 정보들이 담겨있습니다.
     * @author 유호철
     */
    void modifyRepairSchedule(ModifyScheduleRequestDto modify);

    /**
     * 날짜를 통해 스케줄들을 조회하기위한 메서드입니다.
     *
     * @param dto 조회할 일자가 입력됩니다.
     * @return list 조회된 스케줄정보들이 반환됩니다.
     * @author 유호철
     */
    Page<GetRepairScheduleResponseDto> findSchedulesByDate(
        RepairScheduleRequestDto dto, Pageable pageable);

    /**
     * 페이징처리를 위한 스케줄정보가 담겨있습니다.
     *
     * @param pageable 요청 페이지 정보가들어갑니다.
     * @return page 페이지정보가 담긴 수리스케줄이 반환됩니다.
     * @author 유호철
     */
    Page<GetRepairScheduleResponseDto> findRepairSchedules(Pageable pageable);

    /**
     * 매일 일일 수리 설치 분량을 채우기위한 메서드입니다.
     */
    void initializeDailyRepairInstallSchedule();
}
