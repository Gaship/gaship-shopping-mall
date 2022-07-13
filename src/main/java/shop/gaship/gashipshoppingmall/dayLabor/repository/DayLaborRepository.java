package shop.gaship.gashipshoppingmall.dayLabor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.dayLabor.entity.DayLabor;
import shop.gaship.gashipshoppingmall.dayLabor.repository.custom.DayLaborRepositoryCustom;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.repository
 * fileName       :
 * DayLaborRepository
 * author         : HoChul
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/09        HoChul     최초 생성
 */
public interface DayLaborRepository extends JpaRepository<DayLabor,Integer>,
    DayLaborRepositoryCustom {

}
