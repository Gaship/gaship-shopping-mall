package shop.gaship.gashipshoppingmall.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.member.dto.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.dummy.MemberDummy;
import shop.gaship.gashipshoppingmall.member.entity.Member;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.repository <br/>
 * fileName       : MemberRepositoryTest <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/10 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/10        김민수               최초 생성                         <br/>
 */
@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    private Member memberDummy;

    @BeforeEach
    void setUp() {
        this.memberDummy = MemberDummy.dummy();
    }

    @Test
    @DisplayName("멤버 엔티티 jpa 테스트")
    void saveAndCheck() {
        entityManager.persist(memberDummy.getMemberGrades().getRenewalPeriodStatusCode());
        entityManager.persist(memberDummy.getMemberStatusCodes());
        entityManager.persist(memberDummy.getMemberGrades());
        entityManager.persist(memberDummy.getUserAuthorityNo());

        Member savedDummy = memberRepository.save(memberDummy);
        Member member = memberRepository.findById(savedDummy.getMemberNo()).orElse(null);

        assertThat(member).isEqualTo(savedDummy);
    }

    @Test
    @DisplayName("custom query findByEmail 테스트")
    void findByEmailTest() {
        // 임의의 멤버 한명 저장
        entityManager.persist(memberDummy.getMemberGrades().getRenewalPeriodStatusCode());
        entityManager.persist(memberDummy.getMemberStatusCodes());
        entityManager.persist(memberDummy.getMemberGrades());
        entityManager.persist(memberDummy.getUserAuthorityNo());

        Member cachedMember = memberRepository.save(memberDummy);

        Member member = memberRepository.findByEmail(memberDummy.getEmail())
            .orElse(null);

        assertThat(member).isEqualTo(cachedMember);
    }

    @Test
    @DisplayName("custom query findByEmail Optional 테스트")
    void findByEmailNotFoundTest() {
        Member member = memberRepository.findByEmail("abc@nhn.com")
            .orElse(null);

        assertThat(member).isNull();
    }

    @Test
    @DisplayName("custom query findByNickname Optional 테스트")
    void findByNicknameTest() {
        // 임의의 멤버 한명 저장
        entityManager.persist(memberDummy.getMemberGrades().getRenewalPeriodStatusCode());
        entityManager.persist(memberDummy.getMemberStatusCodes());
        entityManager.persist(memberDummy.getMemberGrades());
        entityManager.persist(memberDummy.getUserAuthorityNo());

        Member cachedMember = memberRepository.save(memberDummy);

        Member member = memberRepository.findByNickname(memberDummy.getNickname())
            .orElse(null);

        assertThat(member).isNotNull()
            .isEqualTo(cachedMember);
    }

    @Test
    @DisplayName("custom query findSignInUserDetail Optional 테스트")
    void findSignInUserDetailTest() {
        entityManager.persist(memberDummy.getMemberGrades().getRenewalPeriodStatusCode());
        entityManager.persist(memberDummy.getMemberStatusCodes());
        entityManager.persist(memberDummy.getMemberGrades());
        entityManager.persist(memberDummy.getUserAuthorityNo());

        Member cachedMember = memberRepository.save(memberDummy);

        SignInUserDetailsDto userDetailsDto = memberRepository.findSignInUserDetail("example@nhn.com")
            .orElse(null);

        assertThat(userDetailsDto.getEmail()).isEqualTo(cachedMember.getEmail());
        assertThat(userDetailsDto.getIdentifyNo()).isEqualTo(cachedMember.getMemberNo());
        assertThat(userDetailsDto.getHashedPassword()).isEqualTo(cachedMember.getPassword());
        assertThat(userDetailsDto.getAuthorities())
            .isEqualTo(List.of(cachedMember.getUserAuthorityNo().getStatusCodeName()));
    }
}
