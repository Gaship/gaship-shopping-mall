package shop.gaship.gashipshoppingmall.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.gaship.gashipshoppingmall.member.entity.Member;


/**
 * 회원의 데이터베이스 적합성에 쿼리데 대해 접근.
 */
public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryCustom {
}
