package shop.gaship.gashipshoppingmall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}
