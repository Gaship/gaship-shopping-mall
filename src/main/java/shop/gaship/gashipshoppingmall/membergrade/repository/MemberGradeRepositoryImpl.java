package shop.gaship.gashipshoppingmall.membergrade.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.entity.QMemberGrade;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.repository
 * fileName       : MemberGradeRepositoryImpl
 * author         : Semi Kim
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        Semi Kim       최초 생성
 */
public class MemberGradeRepositoryImpl extends QuerydslRepositorySupport
        implements MemberGradeRepositoryCustom {
    public MemberGradeRepositoryImpl() {
        super(MemberGrade.class);
    }
    /**
     * Gets member grade by.
     *
     * @param memberGradeNo 단건 조회하려는 회원등급 식별 번호 (Integer)
     * @return the member grade by
     */

    @Override
    public Optional<MemberGradeResponseDto> getMemberGradeBy(Integer memberGradeNo) {
        QMemberGrade memberGrade = QMemberGrade.memberGrade;

        return Optional.ofNullable(from(memberGrade)
                .innerJoin(memberGrade.renewalPeriodStatusCode)
                .where(memberGrade.no.eq(memberGradeNo))
                .select(Projections.bean(MemberGradeResponseDto.class,
                        memberGrade.name,
                        memberGrade.accumulateAmount,
                        memberGrade.renewalPeriodStatusCode.statusCodeName))
                .fetchOne());
    }

    /**
     * Gets member grades.
     *
     * @param pageable the pageable
     * @return the member grades
     */
    @Override
    public Page<MemberGradeResponseDto> getMemberGrades(Pageable pageable) {
        QMemberGrade memberGrade = QMemberGrade.memberGrade;

        QueryResults<MemberGradeResponseDto> results =
                from(memberGrade)
                        .innerJoin(memberGrade.renewalPeriodStatusCode)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                .select(Projections.bean(MemberGradeResponseDto.class,
                memberGrade.no,
                memberGrade.name,
                memberGrade.accumulateAmount,
                memberGrade.renewalPeriodStatusCode.statusCodeName))
                .fetchResults();

        List<MemberGradeResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
