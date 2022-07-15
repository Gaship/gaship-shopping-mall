package shop.gaship.gashipshoppingmall.addressLocal.repository.custom;

import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;

import java.util.List;

/**
 * 주소지에대한 QueryDsl 을 쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface AddressLocalRepositoryCustom {

    /**
     * 입력된 주소를 통해 전체주소를 찾는 메서드입니다.
     *
     * @param address : 찾기위한 주소입니다.
     * @return list : 입력된 정보를 통해 조회된 주소들이 반환됩니다.
     */
    List<GetAddressLocalResponseDto> findAllAddress(String address);
}
