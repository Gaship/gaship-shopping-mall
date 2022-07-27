package shop.gaship.gashipshoppingmall.member.repository;

import com.querydsl.core.types.Projections;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;

/**
 * MemberRepositoryCustom 인터페이스에서 제작한 커스텀 쿼리를 구현하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
public class MemberRepositoryImpl extends QuerydslRepositorySupport
    implements MemberRepositoryCustom {
    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        QMember member = QMember.member;

        return Optional.ofNullable(
            from(member)
                .where(member.email.eq(email))
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

        return
            Optional.ofNullable(from(member)
                .where(member.email.eq(email))
                .select(
                    Projections.constructor(SignInUserDetailsDto.class,
                        member.memberNo,
                        member.email,
                        member.password,
                        Projections.list(member.userAuthorityNo.statusCodeName))
                )
                .fetchOne()
            );
    }

    @Override
    public List<AdvancementTargetResponseDto>
        findMembersByNextRenewalGradeDate(LocalDate nextRenewalGradeDate) {
        
        QMember member = QMember.member;

        return from(member)
                .where(member.nextRenewalGradeDate.eq(nextRenewalGradeDate))
                .select(Projections.bean(AdvancementTargetResponseDto.class,
                                member.memberNo,
                                member.nextRenewalGradeDate)
                        )
                .fetch();
    }
}
