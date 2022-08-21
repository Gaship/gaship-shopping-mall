package shop.gaship.gashipshoppingmall.addresslocal.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressSubLocalResponseDto;


/**
 * 주소지를 Service 레이어에서 다루기위한 인터페이스 입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface AddressLocalService {

    /**
     * 배달가능여부를 수정을 위한 메서드입니다.
     *
     * @param modifyDto 조회를할 주소지정보와 배달가능여부가들어있습니다.
     * @author 유호철
     */
    void modifyLocalDelivery(ModifyAddressRequestDto modifyDto);

    List<AddressLocalResponseDto> findAddressLocals();

    List<AddressSubLocalResponseDto> findSubLocals(String upperAddress);
}
