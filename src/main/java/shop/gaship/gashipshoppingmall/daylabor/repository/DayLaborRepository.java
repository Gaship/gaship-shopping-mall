package shop.gaship.gashipshoppingmall.daylabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.daylabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.daylabor.repository.custom.DayLaborRepositoryCustom;

/**
 * 지역별 물량을 다루기위한 레포지토리 인터페이스 입니다.
 * JPA 를 사용하고 QueryDsl 을 사용합니다.
 *
 * @author : 유호철
 * @see JpaRepository
 * @see DayLaborRepositoryCustom
 * @since 1.0
 */
public interface DayLaborRepository extends JpaRepository<DayLabor, Integer>,
    DayLaborRepositoryCustom {

}
