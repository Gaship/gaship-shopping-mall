package shop.gaship.gashipshoppingmall.addressLocal.repository.custom;

import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;

import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.repository.custom
 * fileName       : AddressLocalRepositoryCustom
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
public interface AddressLocalRepositoryCustom {

    List<GetAddressLocalResponseDto> findAllAddress(String address);
}
