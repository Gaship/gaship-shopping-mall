package shop.gaship.gashipshoppingmall.addressLocal.service.impl;

import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

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
     * 배송지 정보를 찾기위한 메소드입니다.
     *
     * @param address 검색할 주소지가 기입되어있습니다.
     * @return list : 검색된 주소지들이 반환됩니다.
     * @author 유호철
     */
    @Override
    public PageResponse<GetAddressLocalResponseDto> findAddressLocals(
        String address, Pageable pageable) {
        return repository.findAllAddress(address, pageable);
    }
}
