package shop.gaship.gashipshoppingmall.addresslocal.service;

import java.util.List;
import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressSubLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressUpperLocalResponseDto;


/**
 * 주소지를 Service 레이어에서 다루기위한 인터페이스 입니다.
 *
 * @author : 유호철
 * @author : 김세미
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

    /**
     * 최상위 주소 "서울" 등을 뽑아오기위한 메서드입니다.
     *
     * @return 주소지 이름과 번호 배송가능여부가 반환됩니다.
     */
    List<AddressUpperLocalResponseDto> findAddressLocals();

    /**
     * level 1 미만의 주소지들이 반환됩니다.
     * 기입된 주소를 기준으로 하위주소지들이 반환됩니다.
     *
     * @param upperAddress 상위주소가 기입됩니다.
     * @return 주소지의 이름과 번호가 반환됩니다.
     */
    List<AddressSubLocalResponseDto> findSubLocals(String upperAddress);

    AddressSubLocalResponseDto findAddressLocalSub(String sigungu);
}
