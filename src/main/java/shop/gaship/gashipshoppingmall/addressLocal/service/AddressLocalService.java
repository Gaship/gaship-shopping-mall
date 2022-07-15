package shop.gaship.gashipshoppingmall.addressLocal.service;

import shop.gaship.gashipshoppingmall.addressLocal.dto.request.AddressSearchRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;

import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.service
 * fileName       : AddressLocalService
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */
public interface AddressLocalService {

    /**
     * methodName : modifyLocalDelivery
     * author : 유호철
     * description : 배송여부를 수정하기위한 메소드
     *
     * @param modifyDto ModifyAddressRequestDto
     */
    void modifyLocalDelivery(ModifyAddressRequestDto modifyDto);

    /**
     * methodName : searchAddress
     * author : 유호철
     * description : 하위 주소지를 찾기위한 메소드
     *
     * @param requestDto AddressSearchRequestDto
     * @return list
     */
    List<GetAddressLocalResponseDto> searchAddress(AddressSearchRequestDto requestDto);
}
