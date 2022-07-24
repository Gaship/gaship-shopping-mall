package shop.gaship.gashipshoppingmall.member.repository;

import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;

/**
 * 커스텀 JPQL 쿼리가필요 한 메서드들을 담은 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@NoRepositoryBean
public interface MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<SignInUserDetailsDto> findSignInUserDetail(String email);
}
