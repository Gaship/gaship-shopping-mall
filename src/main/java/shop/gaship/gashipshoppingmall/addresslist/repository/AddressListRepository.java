package shop.gaship.gashipshoppingmall.addresslist.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import shop.gaship.gashipshoppingmall.addresslist.entity.AddressList;

import javax.persistence.Index;
import java.util.function.Function;

/**
 * 배송지목록의 db 에 접근하기 위한 repository 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface AddressListRepository extends JpaRepository<AddressList, Integer> {
    /**
     * 등록된 배송지의 정보를 보여주기 위해 데이터를 변환하는 메서드입니다.
     *
     * @param memberNo       반환하기 원하는 배송지목록의 상태값을 담고있습니다.
     * @param statusCodeName 배송지목록의 상태값을 담고있습니다.
     * @param pageable       반환하기 원하는 배송지목록 페이지의 정보를 담고있습니다.
     * @return 상태가 statusCondition 인 배송지목록을 pageable 조건에 따라 가져옵니다.
     */

    Page<AddressList> findByMember_MemberNoAndStatusCode_StatusCodeName(Integer memberNo, String statusCodeName, Pageable pageable);
}
