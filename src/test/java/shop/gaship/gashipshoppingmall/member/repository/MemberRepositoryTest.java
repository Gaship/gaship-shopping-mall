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
@TestPropertySource("classpath:application-test.properties")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    private Member memberDummy;

    @BeforeEach
    void setUp() {
        this.memberDummy = MemberDummy.dummy();
    }

    @Test
    void saveAndCheck() {
        Member savedDummy = memberRepository.saveAndFlush(memberDummy);
        Member member = memberRepository.findById(1L).get();

        assertThat(member.getName()).isEqualTo(savedDummy.getName());
    }
}
