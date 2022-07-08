package shop.gaship.gashipshoppingmall.addressLocal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.addressLocal.entity.AddressLocal;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.repository fileName       :
 * AddressLocalRepository author         : HoChul date           : 2022/07/09 description    :
 * =========================================================== DATE              AUTHOR NOTE
 * ----------------------------------------------------------- 2022/07/09        HoChul 최초 생성
 */
public interface AddressLocalRepository extends JpaRepository<AddressLocal,Integer> {

    /**
     * methodName : findByLevel author : Hochul
     * description : level 별로 지역들을 조회
     *
     * @param level
     * @return list
     */
    List<AddressLocal> findByLevel(Integer level);

}
