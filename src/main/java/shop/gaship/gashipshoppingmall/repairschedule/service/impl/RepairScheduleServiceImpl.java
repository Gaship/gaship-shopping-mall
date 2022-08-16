package shop.gaship.gashipshoppingmall.repairschedule.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
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
 * 수리설치일자에대한 데이터 처리를 위한 서비스 클래스입니다.
 *
 * @author : 유호철
 * @author : 김민수
 * @since 1.0
 */

@Service
@RequiredArgsConstructor
public class RepairScheduleServiceImpl implements RepairScheduleService {

    private final RepairScheduleRepository repository;

    private final DayLaborRepository dayLaborRepository;

    /**
     * {@inheritDoc}
     *
     * @param dto 생성할 스케줄에대한 정보들이 담겨있습니다.
     */
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

    /**
     * {@inheritDoc}
     *
     * @param modify 수정할 스케줄에대한 정보들이 담겨있습니다.
     */
    @Transactional
    @Override
    public void modifyRepairSchedule(ModifyScheduleRequestDto modify) {
        RepairSchedule repairSchedule = repository.findByPk(modify.getLocalNo(),
                modify.getDate())
            .orElseThrow(NotExistSchedule::new);

        repairSchedule.fixLabor(modify.getLabor());
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public Page<GetRepairScheduleResponseDto> findSchedulesByDate(
        RepairScheduleRequestDto dto, Pageable pageable) {
        return repository.findAllByDate(dto, pageable);
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public Page<GetRepairScheduleResponseDto> findRepairSchedules(Pageable pageable) {

        return repository.findAllSortDate(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void initializeDailyRepairInstallSchedule() {
        List<DayLabor> dayLabors = dayLaborRepository.findAll();

        List<RepairSchedule> dailyRepairSchedules = dayLabors.stream()
            .map(dayLabor -> {
                RepairSchedulePk repairSchedulePk =
                    new RepairSchedulePk(LocalDate.now(), dayLabor.getAddressNo());

                return RepairSchedule.builder()
                    .pk(repairSchedulePk)
                    .labor(dayLabor.getMaxLabor())
                    .dayLabor(dayLabor)
                    .build();
            })
            .collect(Collectors.toUnmodifiableList());

        repository.saveAll(dailyRepairSchedules);
    }
}
