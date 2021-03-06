package shop.gaship.gashipshoppingmall.repairSchedule.service.impl;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.exception.NotExistDayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.RepairSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.entity.pk.RepairSchedulePk;
import shop.gaship.gashipshoppingmall.repairSchedule.exception.AlreadyExistSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.exception.NotExistSchedule;
import shop.gaship.gashipshoppingmall.repairSchedule.repository.RepairScheduleRepository;
import shop.gaship.gashipshoppingmall.repairSchedule.service.RepairScheduleService;

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
    public List<GetRepairScheduleResponseDto> findSchedulesByDate(LocalDate now) {
        return repository.findAllByDate(now);
    }

    @Override
    public Page<GetRepairScheduleResponseDto> findRepairSchedules(int page, int size) {

        return repository.findAllSortDate(PageRequest.of(page, size));
    }
}
