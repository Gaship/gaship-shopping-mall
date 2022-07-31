package shop.gaship.gashipshoppingmall.daylabor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.daylabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.daylabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.daylabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.exception.NotExistDayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.DayLaborRepository;
import shop.gaship.gashipshoppingmall.daylabor.service.DayLaborService;
import shop.gaship.gashipshoppingmall.employee.exception.WrongAddressException;
import shop.gaship.gashipshoppingmall.response.PageResponse;

/**
 * 서비스레이어에서 지역별물량을 사용하기위한 구현체입니다.
 *
 * @author : 유호철
 * @see DayLaborService
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
    public void addDayLabor(CreateDayLaborRequestDto dto) {
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
     * @param fixDto 수정할 지역정보와 물량이 들어있습니다
     * @author 유호철
     */
    @Transactional
    @Override
    public void modifyDayLabor(FixDayLaborRequestDto fixDto) {
        DayLabor dayLabor =
            repository.findById(fixDto.getLocalNo()).orElseThrow(NotExistDayLabor::new);
        dayLabor.fixMaxLabor(fixDto);
    }

    /**
     * 전체 지역별물량을 조회하기위한 메서드입니다.
     *
     * @return list 반환될 지역들과 최대물량이 들어있습니다.
     * @author 유호철
     */
    @Override
    public PageResponse<GetDayLaborResponseDto> findDayLabors(Pageable pageable) {
        return repository.findAllDayLabor(pageable);
    }
}
