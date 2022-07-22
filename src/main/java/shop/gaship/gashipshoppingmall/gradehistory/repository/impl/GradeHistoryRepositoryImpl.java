package shop.gaship.gashipshoppingmall.gradehistory.repository.impl;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;
import shop.gaship.gashipshoppingmall.gradehistory.entity.QGradeHistory;
import shop.gaship.gashipshoppingmall.gradehistory.repository.GradeHistoryRepositoryCustom;

/**
 * 등급이력 custom repository 구현체.
 *
 * @author : 김세미
 * @see shop.gaship.gashipshoppingmall.gradehistory.repository.GradeHistoryRepositoryCustom
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
 * @since 1.0
 */
public class GradeHistoryRepositoryImpl
        extends QuerydslRepositorySupport implements GradeHistoryRepositoryCustom {

    public GradeHistoryRepositoryImpl() {
        super(GradeHistory.class);
    }

    @Override
    public Page<GradeHistoryResponseDto>
        getGradeHistoriesByMember(Integer memberNo, PageRequest pageRequest) {

        QGradeHistory gradeHistory = QGradeHistory.gradeHistory;

        QueryResults<GradeHistoryResponseDto> queryResults = from(gradeHistory)
                .where(gradeHistory.member.memberNo.eq(memberNo))
                .orderBy(gradeHistory.at.desc())
                .limit(pageRequest.getPageSize())
                .offset(pageRequest.getOffset())
                .select(Projections.bean(GradeHistoryResponseDto.class,
                        gradeHistory.at,
                        gradeHistory.totalAmount,
                        gradeHistory.gradeName))
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageRequest, queryResults.getTotal());
    }
}
