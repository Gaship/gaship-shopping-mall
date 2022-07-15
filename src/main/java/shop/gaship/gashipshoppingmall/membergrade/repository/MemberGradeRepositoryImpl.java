package shop.gaship.gashipshoppingmall.membergrade.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
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

    @Override
    public List<MemberGradeResponseDto> getMemberGrades(Pageable pageable) {
        QMemberGrade memberGrade = QMemberGrade.memberGrade;

        return from(memberGrade)
                .innerJoin(memberGrade.renewalPeriodStatusCode)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .select(Projections.bean(MemberGradeResponseDto.class,
                        memberGrade.no,
                        memberGrade.name,
                        memberGrade.accumulateAmount,
                        memberGrade.renewalPeriodStatusCode.statusCodeName))
                .fetch();
    }
}