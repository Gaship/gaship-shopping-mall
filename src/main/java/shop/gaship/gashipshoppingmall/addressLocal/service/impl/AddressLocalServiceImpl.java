package shop.gaship.gashipshoppingmall.addressLocal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.AddressSearchRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.exception.NotExistAddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.AddressLocalRepository;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;

import javax.transaction.Transactional;
import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.service.impl fileName       :
 * AddressLocalServiceipml author         : 유호철 date           : 2022/07/12 description    :
 * =========================================================== DATE              AUTHOR
 * NOTE ----------------------------------------------------------- 2022/07/12        유호철       최초
 * 생성
 */
@Service
@RequiredArgsConstructor
public class AddressLocalServiceImpl implements AddressLocalService {

    private final AddressLocalRepository repository;

    @Transactional
    @Override
    public void modifyLocalDelivery(ModifyAddressRequestDto modifyDto) {
        AddressLocal addressLocal = repository.findById(modifyDto.getLocalNo())
                .orElseThrow(NotExistAddressLocal::new);

        addressLocal.allowDelivery(modifyDto.isDelivery());
        repository.save(addressLocal);
    }

    @Override
    public List<GetAddressLocalResponseDto> searchAddress(AddressSearchRequestDto requestDto) {
        return repository.findAllAddress(requestDto.getAddress());
    }
}
