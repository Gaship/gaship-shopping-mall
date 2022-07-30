package shop.gaship.gashipshoppingmall.addressLocal.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.response.PageResponse;


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

    /**
     * 주소지를 검색하기위한 메서드입니다.
     *
     * @param address 검색을할 주소지가 입력되어있습니다.
     * @return list : 검색된 주소지와 그 주소지의 하위주소지들이 들어있습니다.
     * @author 유호철
     */
    PageResponse<GetAddressLocalResponseDto> findAddressLocals(String address, Pageable pageable);
}
