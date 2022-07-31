package shop.gaship.gashipshoppingmall.addresslocal.repository.custom;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.response.PageResponse;

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
     * @param address  : 찾기위한 주소입니다.
     * @param pageable : page 에대한 정보가 담겨 있습니다.
     * @return list : 입력된 정보를 통해 조회된 주소들이 반환됩니다.
     */
    PageResponse<GetAddressLocalResponseDto> findAllAddress(String address, Pageable pageable);
}
