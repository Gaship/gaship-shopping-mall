package shop.gaship.gashipshoppingmall.addresslist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;

/**
 * 배송지목록의 db 에 접근하기 위한 repository 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface AddressListRepository
        extends JpaRepository<AddressList, Integer>, AddressListRepositoryCustom {
}
