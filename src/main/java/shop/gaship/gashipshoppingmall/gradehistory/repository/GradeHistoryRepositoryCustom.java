package shop.gaship.gashipshoppingmall.gradehistory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;

/**
 * 등급이력 custom repository.
 *
 * @author : 김세미
 * @since 1.0
 */
public interface GradeHistoryRepositoryCustom {
    /**
     * Gets grade histories by member.
     *
     * @param member 조회하려는 등급이력 대상 회원 (Member)
     * @param pageRequest 페이지 요청 (PageRequest)
     * @return the grade histories by member
     */
    Page<GradeHistoryResponseDto> getGradeHistoriesByMember(Member member, PageRequest pageRequest);
}
