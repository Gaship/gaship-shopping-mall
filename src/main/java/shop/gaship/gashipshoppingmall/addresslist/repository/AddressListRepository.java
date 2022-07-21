package shop.gaship.gashipshoppingmall.addresslist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;

/**
 * @author 최정우
 * @since 1.0
 */
public interface AddressListRepository extends JpaRepository<AddressList,Integer> {
}
