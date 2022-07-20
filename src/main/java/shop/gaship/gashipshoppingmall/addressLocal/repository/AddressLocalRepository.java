package shop.gaship.gashipshoppingmall.addressLocal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;
import shop.gaship.gashipshoppingmall.addressLocal.repository.custom.AddressLocalRepositoryCustom;

import java.util.List;

/**
 * 주소지를 다루기위한 레포지토리 인터페이스 입니다.
 * JPA 를 사용하고 QueryDsl 을 사용합니다.
 *
 * @see JpaRepository
 * @see AddressLocalRepositoryCustom
 * @author : 유호철
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

}
