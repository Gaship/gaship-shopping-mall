package shop.gaship.gashipshoppingmall.dayLabor.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.exception.NotExistDayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.dayLabor.service.DayLaborService;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.service.impl
 * fileName       : DayLaborServiceImpl
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초생성
 */

@Service
@RequiredArgsConstructor
public class DayLaborServiceImpl implements DayLaborService {

    private final DayLaborRepository repository;

    private final AddressLocalRepository localRepository;

    @Transactional
    @Override
    public void createDayLabor(CreateDayLaborRequestDto dto) {
        if (localRepository.findById(dto.getLocalNo()).isEmpty()) {
            throw new WrongAddressException();
        }
        DayLabor dayLabor = new DayLabor();
        dayLabor.registerDayLabor(dto);

        repository.save(dayLabor);
    }

    @Transactional
    @Override
    public void modifyDayLabor(FixDayLaborRequestDto fixDto) {
        DayLabor dayLabor = repository.findById(fixDto.getLocalNo()).orElseThrow(NotExistDayLabor::new);
        dayLabor.fixMaxLabor(fixDto);
        repository.save(dayLabor);
    }

    @Override
    public List<GetDayLaborResponseDto> getAllDayLabors() {
        return repository.findAllDayLabor();
    }
}
