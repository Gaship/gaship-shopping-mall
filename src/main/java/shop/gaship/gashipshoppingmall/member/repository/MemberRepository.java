package shop.gaship.gashipshoppingmall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;

import java.util.Optional;

/**
 * 회원 테이블에 JPA를 통해서 접근가능한 클래스입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberRepository extends JpaRepository<Member,Integer>, MemberRepositoryCustom{
    Optional<Member> findByNickname(String recommendMemberNickname);

    boolean existsByNickname(String nickname);
}
