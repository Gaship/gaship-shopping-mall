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
    Page<GradeHistoryResponseDto> getGradeHistoriesByMember(Member member, PageRequest of);
}
