package shop.gaship.gashipshoppingmall.membergrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.repository
 * fileName       : MemberGradeRepository
 * author         : semi
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        semi       최초 생성
 */
public interface MemberGradeRepository
        extends JpaRepository<MemberGrade, Integer> {
}
