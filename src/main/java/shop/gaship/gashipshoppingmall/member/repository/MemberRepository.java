package shop.gaship.gashipshoppingmall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer> {
    Optional<Member> findByNickname(String recommendMemberNickname);
}
