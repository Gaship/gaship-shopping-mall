package shop.gaship.gashipshoppingmall.dayLabor.service.impl;

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

import javax.transaction.Transactional;
import java.util.List;

/**
 * 서비스레이어에서 지역별물량을 사용하기위한 구현체입니다.
 *
 * @see DayLaborService
 * @author : 유호철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DayLaborServiceImpl implements DayLaborService {

    private final DayLaborRepository repository;

    private final AddressLocalRepository localRepository;

    /**
     * 지역별일자를 생성하기위한 메소드입니다.
     *
     * @param dto 지역번호와 최대물량이 담겨져있씁니다.
     * @author 유호철
     */
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

    /**
     * 지역별물량을 수정하기위한 메서드입니다.
     *
     *
     * @param fixDto 수정할 지역정보와 물량이 들어있습니다
     * @author 유호철
     */
    @Transactional
    @Override
    public void modifyDayLabor(FixDayLaborRequestDto fixDto) {
        DayLabor dayLabor = repository.findById(fixDto.getLocalNo()).orElseThrow(NotExistDayLabor::new);
        dayLabor.fixMaxLabor(fixDto);
        repository.save(dayLabor);
    }

    /**
     * 전체 지역별물량을 조회하기위한 메서드입니다.
     *
     * @return list 반환될 지역들과 최대물량이 들어있습니다.
     * @author 유호철
     */
    @Override
    public List<GetDayLaborResponseDto> getAllDayLabors() {
        return repository.findAllDayLabor();
    }
}
