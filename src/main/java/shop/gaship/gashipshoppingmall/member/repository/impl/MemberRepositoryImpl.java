package shop.gaship.gashipshoppingmall.member.repository.impl;

import com.querydsl.core.types.Projections;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDtoByAdmin;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepositoryCustom;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;

/**
 * MemberRepositoryCustom 인터페이스에서 제작한 커스텀 쿼리를 구현하는 클래스입니다.
 *
 * @author 김민수
 * @author 조재철
 * @author 김세미
 * @since 1.0
 */
public class MemberRepositoryImpl extends QuerydslRepositorySupport
        implements MemberRepositoryCustom {
    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Optional<Member> findByEncodedEmailForSearch(String email) {
        QMember member = QMember.member;

        return Optional.ofNullable(
                from(member)
                        .where(member.encodedEmailForSearch.eq(email))
                        .select(member)
                        .fetchOne()
        );
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        QMember member = QMember.member;

        return Optional.ofNullable(
                from(member)
                        .where(member.nickname.eq(nickname))
                        .select(member)
                        .fetchOne()
        );
    }

    @Override
    public Optional<SignInUserDetailsDto> findSignInUserDetail(String email) {
        QMember member = QMember.member;

        Member result = from(member)
                .where(member.email.eq(email))
                .select(member)
                .fetchOne();

        return Optional.ofNullable(
                SignInUserDetailsDto.builder()
                        .memberNo(result.getMemberNo())
                        .email(result.getEmail())
                        .hashedPassword(result.getPassword())
                        .authorities(result.getRoleSet().stream()
                                .map(String::valueOf)
                                .collect(Collectors.toList()))
                        .build()
        );
    }

    @Override
    public List<AdvancementTargetResponseDto> findMembersByNextRenewalGradeDate(
            LocalDate nextRenewalGradeDate) {

        QMember member = QMember.member;

        return from(member)
                .where(member.nextRenewalGradeDate.eq(nextRenewalGradeDate))
                .select(Projections.bean(AdvancementTargetResponseDto.class,
                        member.memberNo,
                        member.nextRenewalGradeDate)
                )
                .fetch();
    }

    @Override
    public Page<MemberResponseDtoByAdmin> findMembers(Pageable pageable) {
        QMember member = QMember.member;

        List<MemberResponseDtoByAdmin> content =
                from(member)
                        .limit(Math.min(pageable.getPageSize(), 30))
                        .offset(pageable.getOffset())
                        .orderBy(member.memberNo.desc())
                        .select(Projections.constructor(MemberResponseDtoByAdmin.class,
                                member.memberNo,
                                member.recommendMember.name.as("recommendMemberName"),
                                member.memberStatusCodes.statusCodeName.as("memberStatus"),
                                member.memberGrades.name.as("memberGrade"),
                                member.email,
                                member.phoneNumber,
                                member.nickname,
                                member.gender,
                                member.birthDate,
                                member.accumulatePurchaseAmount,
                                member.nextRenewalGradeDate,
                                member.registerDatetime,
                                member.modifyDatetime,
                                member.isSocial.as("social")))
                        .fetch();

        return PageableExecutionUtils.getPage(content,
                pageable,
                () -> from(member)
                        .fetch()
                        .size());
    }


}
