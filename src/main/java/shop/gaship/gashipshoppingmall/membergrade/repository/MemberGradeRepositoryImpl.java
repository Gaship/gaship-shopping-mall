package shop.gaship.gashipshoppingmall.membergrade.repository;

import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.entity.QMemberGrade;


/**
 * 회원등급 Custom Repository 구현체.
 *
 * @author : 김세미
 * @since 1.0
 * @see shop.gaship.gashipshoppingmall.membergrade.repository.MemberGradeRepositoryCustom
 * @see org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
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
    public Page<MemberGradeResponseDto> getMemberGrades(Pageable pageable) {
        QMemberGrade memberGrade = QMemberGrade.memberGrade;

        List<MemberGradeResponseDto> content =
                from(memberGrade)
                        .innerJoin(memberGrade.renewalPeriodStatusCode)
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                .select(Projections.bean(MemberGradeResponseDto.class,
                memberGrade.no,
                memberGrade.name,
                memberGrade.accumulateAmount,
                memberGrade.renewalPeriodStatusCode.statusCodeName))
                        .fetch();

        return PageableExecutionUtils.getPage(content,
                pageable,
                () -> from(memberGrade)
                        .innerJoin(memberGrade.renewalPeriodStatusCode)
                        .fetch().size());
    }

    @Override
    public MemberGrade findByDefaultGrade() {
        QMemberGrade memberGrade = QMemberGrade.memberGrade;

        return from(memberGrade)
            .where(memberGrade.isDefault.eq(true))
            .select(memberGrade)
            .fetchOne();
    }

    @Override
    public List<MemberGradeResponseDto> getAll() {
        QMemberGrade memberGrade = QMemberGrade.memberGrade;

        return from(memberGrade)
                .innerJoin(memberGrade.renewalPeriodStatusCode)
                .select(Projections.bean(MemberGradeResponseDto.class,
                        memberGrade.no,
                        memberGrade.name,
                        memberGrade.accumulateAmount,
                        memberGrade.renewalPeriodStatusCode.statusCodeName))
                .fetch();
    }
}
