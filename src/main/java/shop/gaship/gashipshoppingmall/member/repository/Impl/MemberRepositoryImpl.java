package shop.gaship.gashipshoppingmall.member.repository.Impl;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.QMember;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepositoryCustom;

/**
 * MemberRepositoryCustom 인터페이스에서 제작한 커스텀 쿼리를 구현하는 클래스입니다.
 *
 * @author 김민수
 * @author 조재철
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
}
