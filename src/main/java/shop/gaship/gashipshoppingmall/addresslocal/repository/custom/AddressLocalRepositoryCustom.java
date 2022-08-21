package shop.gaship.gashipshoppingmall.addresslocal.repository.custom;

import java.util.List;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressSubLocalResponseDto;

/**
 * 주소지에대한 QueryDsl 을 쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface AddressLocalRepositoryCustom {


    /**
     * 최상위 주소지들을 반환하기위한 메서드입니다.
     *
     * @return 주소지들의 정보가 반환됩니다.
     */
    List<AddressLocalResponseDto> findAllAddress();

    /**
     * 하위주소지들을 반환하기위한 메서드입니다.
     *
     * @param upperAddress 상위주소지의 이름이 기입됩니다.
     * @return the list
     */
    List<AddressSubLocalResponseDto> findSubAddress(String upperAddress);
}
