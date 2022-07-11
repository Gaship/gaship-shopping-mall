package shop.gaship.gashipshoppingmall.membergrade.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.request.MemberGradeRequest;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static shop.gaship.gashipshoppingmall.membergrade.utils.CreateTestUtils.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.repository
 * fileName       : MemberGradeRepositoryTest
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
@DataJpaTest
class MemberGradeRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MemberGradeRepository memberGradeRepository;

    private StatusCode renewalPeriod;
    private MemberGrade memberGrade;

    @BeforeEach
    void setUp() {
        MemberGradeRequest memberGradeRequest = createTestMemberGradeRequest("일반", 0L);
        renewalPeriod = createTestStatusCode();
        memberGrade = createTestMemberGrade(memberGradeRequest, renewalPeriod);
    }

    @Test
    void insertMemberGrade() {
        // when
        testEntityManager.persist(renewalPeriod);
        MemberGrade result = memberGradeRepository.save(memberGrade);

        Long count = memberGradeRepository.count();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNo()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo(memberGrade.getName());
        assertThat(result.getAccumulateAmount()).isEqualTo(memberGrade.getAccumulateAmount());
        assertThat(result.getRenewalPeriodStatusCode()).isEqualTo(memberGrade.getRenewalPeriodStatusCode());

        assertThat(count).isEqualTo(1L);
    }

    @Test
    void updateMemberGrade() {
        // given
        testEntityManager.persist(renewalPeriod);
        MemberGrade newMemberGrade = memberGradeRepository.save(memberGrade);

        // when
        newMemberGrade.setName("새싹");
        newMemberGrade.setAccumulateAmount(1L);
        memberGradeRepository.saveAndFlush(newMemberGrade);
        testEntityManager.clear();

        // then
        Optional<MemberGrade> result = memberGradeRepository.findById(newMemberGrade.getNo());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("새싹");
        assertThat(result.get().getAccumulateAmount()).isEqualTo(1L);
    }

    @Test
    void deleteMemberGrade() {
        // given
        testEntityManager.persist(renewalPeriod);
        MemberGrade newMemberGrade = memberGradeRepository.saveAndFlush(memberGrade);

        // when
        memberGradeRepository.delete(newMemberGrade);
        memberGradeRepository.flush();

        // then
        boolean result = memberGradeRepository.existsById(newMemberGrade.getNo());

        assertThat(result).isFalse();
    }

    @Test
    void getMemberGradeBy_whenMemberGradeIsPresent() {
        // given
        testEntityManager.persist(renewalPeriod);
        MemberGrade newMemberGrade = memberGradeRepository.saveAndFlush(memberGrade);
        testEntityManager.clear();

        // when
        Optional<MemberGradeDto> memberGradeDto =
                memberGradeRepository.getMemberGradeBy(newMemberGrade.getNo());

        // then
        assertThat(memberGradeDto).isPresent();
        assertThat(memberGradeDto.get().getName()).isEqualTo(newMemberGrade.getName());
        assertThat(memberGradeDto.get().getAccumulateAmount()).isEqualTo(newMemberGrade.getAccumulateAmount());
    }

    @Test
    void getMemberGradeBy_whenMemberGradIsEmpty() {
        // given
        Integer testMemberGradeNo = 2;

        // when
        Optional<MemberGradeDto> memberGradeDto =
                memberGradeRepository.getMemberGradeBy(testMemberGradeNo);

        assertThat(memberGradeDto).isEmpty();
    }
}