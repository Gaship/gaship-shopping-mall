package shop.gaship.gashipshoppingmall.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;

/**
 * 회원 테이블에 JPA를 통해서 접근가능한 클래스입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryCustom {
    Optional<Member> findByNickname(String recommendMemberNickname);

    boolean existsByNickname(String nickname);

    boolean existsByMemberNo(Integer memberNo);

    List<Member> findByMemberGrades(MemberGrade memberGrade);

    @Query("SELECT MAX(m.memberNo) From Member m")
    Integer findLastNo();

}
