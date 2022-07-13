package shop.gaship.gashipshoppingmall.member.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
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
@Transactional
class MemberRepositoryTest {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    private Member memberDummy;

    @BeforeEach
    void setUp() {
        this.memberDummy = MemberDummy.dummy();
    }

    @Test
    void saveAndCheck() {
        entityManager.persist(memberDummy.getStatus());
        entityManager.persist(memberDummy.getGrade());

        Member savedDummy = memberRepository.save(memberDummy);
        Member member = memberRepository.findById(1L).orElse(null);

        assertThat(member.getName()).isEqualTo(savedDummy.getName());
    }
}
