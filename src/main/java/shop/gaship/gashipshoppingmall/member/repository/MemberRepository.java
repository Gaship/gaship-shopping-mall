package shop.gaship.gashipshoppingmall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;

import java.util.Optional;

/**
 * tag에 관련된 db 정보를 관리하는 repository입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByNickname(String recommendMemberNickname);
}
