package shop.gaship.gashipshoppingmall.membergrade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;

/**
 * 회원등급 Repository.
 *
 * @author : 김세미
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepositoryCustom
 * @since 1.0
 */
public interface MemberGradeRepository
        extends JpaRepository<MemberGrade, Integer>, MemberGradeRepositoryCustom {
    /**
     * 기본회원등급이 존재하는지 확인.
     *
     * @return boolean 기본회원등급 존재 여부.
     * @author 김세미
     */
    boolean existsByIsDefaultIsTrue();

    /**
     * 특정 기준누적금액에 해당하는 회원등급이 이미 존재하는지 확인.
     *
     * @param accumulateAmount 회원등급 승급 기준누적금액 (Long)
     * @return boolean 기준누적금액에 해당하는 회원등급 존재 여부.
     * @author 김세미
     */
    boolean existsByAccumulateAmountEquals(Long accumulateAmount);
}
