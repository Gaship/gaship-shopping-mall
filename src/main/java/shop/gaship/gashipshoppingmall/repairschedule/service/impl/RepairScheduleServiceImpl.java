package shop.gaship.gashipshoppingmall.repairschedule.service.impl;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.exception.NotExistDayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.RepairScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairschedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.entity.pk.RepairSchedulePk;
import shop.gaship.gashipshoppingmall.repairschedule.exception.AlreadyExistSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.exception.NotExistSchedule;
import shop.gaship.gashipshoppingmall.repairschedule.repository.RepairScheduleRepository;
import shop.gaship.gashipshoppingmall.repairschedule.service.RepairScheduleService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.repairSechedule.service.impl fileName       :
 * * RepairSecheduleServiceImpl author         : 유호철 date           : 2022/07/13 description    :
 * * =========================================================== DATE              AUTHOR NOTE
 * * ----------------------------------------------------------- 2022/07/13       유호철       최초 생성
 */

@Service
@RequiredArgsConstructor
public class RepairScheduleServiceImpl implements RepairScheduleService {

    private final RepairScheduleRepository repository;

    private final DayLaborRepository dayLaborRepository;

    @Transactional
    @Override
    public void addRepairSchedule(CreateScheduleRequestDto dto) {
        if (repository.findByPk(dto.getLocalNo(), dto.getDate()).isPresent()) {
            throw new AlreadyExistSchedule();
        }

        DayLabor dayLabor = dayLaborRepository.findById(dto.getLabor())
            .orElseThrow(NotExistDayLabor::new);

        repository.save(
            RepairSchedule.builder()
                .pk(new RepairSchedulePk(dto.getDate(), dto.getLocalNo()))
                .labor(dto.getLabor())
                .dayLabor(dayLabor)
                .build()
        );
    }

    @Transactional
    @Override
    public void modifyRepairSchedule(ModifyScheduleRequestDto modify) {
        RepairSchedule repairSchedule = repository.findByPk(modify.getLocalNo(),
                modify.getDate())
            .orElseThrow(NotExistSchedule::new);

        repairSchedule.fixLabor(modify.getLabor());
    }

    @Override
    public Page<GetRepairScheduleResponseDto> findSchedulesByDate(
        RepairScheduleRequestDto dto, Pageable pageable) {
        return repository.findAllByDate(dto, pageable);
    }

    @Override
    public Page<GetRepairScheduleResponseDto> findRepairSchedules(Pageable pageable) {

        return repository.findAllSortDate(pageable);
    }
}
