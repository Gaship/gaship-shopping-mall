package shop.gaship.gashipshoppingmall.addresslocal.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addresslocal.repository.impl.AddressSubLocalResponseDto;
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
public class AddressLocalServiceImpl implements AddressLocalService {

    private final AddressLocalRepository repository;

    /**
     * 배송여부를 수정하기위한 메소드입니다.
     *
     * @param modifyDto 조회할 주소지정보와 수정해야할 배송가능여부를 포함합니다.
     * @throws NotExistAddressLocal 주소지를 찾을수없습니다.
     * @author 유호철
     */
    @Transactional
    @Override
    public void modifyLocalDelivery(ModifyAddressRequestDto modifyDto) {
        AddressLocal addressLocal = repository.findById(modifyDto.getLocalNo())
            .orElseThrow(NotExistAddressLocal::new);

        addressLocal.allowDelivery(modifyDto.isDelivery());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressLocalResponseDto> findAddressLocals() {
        return repository.findAllAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AddressSubLocalResponseDto> findSubLocals(String upperAddress) {
        if (Boolean.FALSE.equals(repository.existsByAddressName(upperAddress))) {
            throw new NotExistAddressLocal();
        }
        return repository.findSubAddress(upperAddress);
    }

}
