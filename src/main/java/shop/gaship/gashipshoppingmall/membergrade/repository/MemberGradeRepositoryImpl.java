package shop.gaship.gashipshoppingmall.membergrade.repository;

import com.querydsl.core.types.Projections;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
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
    public Optional<MemberGradeDto> getMemberGradeBy(Integer memberGradeNo) {
        QMemberGrade memberGrade = QMemberGrade.memberGrade;

        return Optional.ofNullable(from(memberGrade)
                .innerJoin(memberGrade.renewalPeriodStatusCode)
                .where(memberGrade.no.eq(memberGradeNo))
                .select(Projections.bean(MemberGradeDto.class,
                        memberGrade.name,
                        memberGrade.accumulateAmount,
                        memberGrade.renewalPeriodStatusCode.statusCodeName))
                .fetchOne());
    }
}
