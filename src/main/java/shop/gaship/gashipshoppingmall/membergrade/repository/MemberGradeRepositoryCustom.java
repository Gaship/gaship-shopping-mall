package shop.gaship.gashipshoppingmall.membergrade.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;

/**
 * 회원등급 Custom Repository.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface MemberGradeRepositoryCustom {
    /**
     * 회원등급 단건 조회.
     *
     * @param memberGradeNo 조회하려는 회원등급 번호 (Integer)
     * @return Optional - MemberGradeResponseDto
     */
    Optional<MemberGradeResponseDto> getMemberGradeBy(Integer memberGradeNo);

    /**
     * 회원등급 페이지 단위 다건 조회.
     *
     * @param pageable the pageable
     * @return Page - MemberGradeResponseDto
     */
    Page<MemberGradeResponseDto> getMemberGrades(Pageable pageable);

    /**
     * 회원의 기본 등급을 조회합니다.
     *
     * @return 회원의 기본등급이 조회됩니다.
     */
    MemberGrade findByDefaultGrade();

    /**
     * 전체 회원등급 다건 조회.
     *
     * @return List - MemberGradeResponseDto
     */
    List<MemberGradeResponseDto> getAll();
}
