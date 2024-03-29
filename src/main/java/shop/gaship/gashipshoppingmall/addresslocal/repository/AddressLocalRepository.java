package shop.gaship.gashipshoppingmall.addresslocal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.addresslocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addresslocal.repository.custom.AddressLocalRepositoryCustom;


/**
 * 주소지를 다루기위한 레포지토리 인터페이스 입니다.
 * JPA 를 사용하고 QueryDsl 을 사용합니다.
 *
 * @author : 유호철
 * @see JpaRepository
 * @see AddressLocalRepositoryCustom
 * @since 1.0
 */
public interface AddressLocalRepository extends JpaRepository<AddressLocal, Integer>,
    AddressLocalRepositoryCustom {


    /**
     * 계층을 통해서 조회를 맞는계층에있는 주소를 다 조회하기위한 메서드입니다.
     *
     * @param level : 계층을 의미합니다.
     * @return list : 계층별로 맞는 주소지가 반환됩니다.
     * @author 유호철
     */
    List<AddressLocal> findByLevel(Integer level);

    /**
     * 주소지가 조재하는지 확인하기 위한 메서드입니다.
     *
     * @param addressName 주소지가 기입됩니다.
     * @return 맞으면 true 없으면 false 가 반환됩니다.
     * @author 유호철
     */
    boolean existsByAddressName(String addressName);

}
