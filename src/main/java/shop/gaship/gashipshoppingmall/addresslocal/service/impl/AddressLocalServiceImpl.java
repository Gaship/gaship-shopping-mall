package shop.gaship.gashipshoppingmall.addresslocal.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressSubLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressUpperLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addresslocal.service.AddressLocalService;

/**
 * 주소를 서비스레이어에서 사용할수있게하는 구현 클래스입니다.
 *
 * @author : 유호철
 * @see AddressLocalService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AddressLocalServiceImpl implements AddressLocalService {

    private final AddressLocalRepository addressLocalRepository;

    /**
     * 배송여부를 수정하기위한 메소드입니다.
     *
     * @param modifyDto 조회할 주소지정보와 수정해야할 배송가능여부를 포함합니다.
     * @throws NotExistAddressLocal 주소지를 찾을수없습니다.
     * @author 유호철
     */
    @Transactional
    @Modifying
    @Override
    public void modifyLocalDelivery(ModifyAddressRequestDto modifyDto) {
        AddressLocal addressLocal = addressLocalRepository.findById(modifyDto.getLocalNo())
            .orElseThrow(NotExistAddressLocal::new);
        log.error("{}", modifyDto.isDelivery());
        addressLocal.allowDelivery(!modifyDto.isDelivery());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressUpperLocalResponseDto> findAddressLocals() {
        return addressLocalRepository.findAllAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressSubLocalResponseDto> findSubLocals(String upperAddress) {
        if (Boolean.FALSE.equals(addressLocalRepository.existsByAddressName(upperAddress))) {
            throw new NotExistAddressLocal();
        }
        return addressLocalRepository.findSubAddress(upperAddress);
    }

}
