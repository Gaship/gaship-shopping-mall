package shop.gaship.gashipshoppingmall.member.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;

/**
 * 커스텀 JPQL 쿼리가필요 한 메서드들을 담은 객체입니다.
 *
 * @author 김민수
 * @author 조재철
 * @since 1.0
 */
@NoRepositoryBean
public interface MemberRepositoryCustom {
    Optional<Member> findByEncodedEmailForSearch(String email);

    Optional<Member> findByNickname(String nickname);

    Optional<SignInUserDetailsDto> findSignInUserDetail(String email);

    List<AdvancementTargetResponseDto>
        findMembersByNextRenewalGradeDate(LocalDate nextRenewalGradeDate);

    Page<MemberResponseDto> findMembers(Pageable pageable);
}
