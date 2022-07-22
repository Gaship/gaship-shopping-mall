package shop.gaship.gashipshoppingmall.gradehistory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;

/**
 * 등급이력 custom repository.
 *
 * @author : 김세미
 * @since 1.0
 */
@NoRepositoryBean
public interface GradeHistoryRepositoryCustom {
    /**
     * Gets grade histories by member.
     *
     * @param memberNo 조회하려는 등급이력 대상 회원 식별번호 (Integer)
     * @param pageRequest 페이지 요청 (PageRequest)
     * @return the grade histories by member
     */
    Page<GradeHistoryResponseDto>
        getGradeHistoriesByMember(Integer memberNo, PageRequest pageRequest);
}
