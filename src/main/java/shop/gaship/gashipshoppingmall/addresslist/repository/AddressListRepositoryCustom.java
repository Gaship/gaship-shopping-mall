package shop.gaship.gashipshoppingmall.addresslist.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.addresslist.dto.response.AddressListResponseDto;

/**
 * 레포지토리 커스텀.
 *
 * @author 최정우
 * @since 1.0
 */
@NoRepositoryBean
public interface AddressListRepositoryCustom {

    /**
     * 회원의 id 값에 따라 배송지목록을 반환하는 메서드 입니다.
     *
     * @param memberNo 조회하려는 배송지목록 회원 식별번호
     * @param pageable 조회하려는 페이지 번호와 사이즈가 있습니다.
     * @return 페이지
     */
    Page<AddressListResponseDto> findAddressListByMemberId(Integer memberNo, Pageable pageable);
}
