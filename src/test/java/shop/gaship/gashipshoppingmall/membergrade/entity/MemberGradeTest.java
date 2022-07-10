package shop.gaship.gashipshoppingmall.membergrade.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.membergrade.request.MemberGradeRequest;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import static org.assertj.core.api.Assertions.assertThat;
import static shop.gaship.gashipshoppingmall.membergrade.utils.CreateTestUtils.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.entity
 * fileName       : MemberGradeTest
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
@DataJpaTest
class MemberGradeTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    void createMemberGrade() {
        // given
        MemberGradeRequest memberGradeRequest = createTestMemberGradeRequest("일반", 0L);
        StatusCode renewalPeriod = createTestStatusCode();
        MemberGrade memberGrade = createTestMemberGrade(memberGradeRequest, renewalPeriod);

        // when
        testEntityManager.persist(renewalPeriod);
        MemberGrade result = testEntityManager.persist(memberGrade);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getNo()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo(memberGrade.getName());
        assertThat(result.getAccumulateAmount()).isEqualTo(memberGrade.getAccumulateAmount());
        assertThat(result.getRenewalPeriodStatusCode()).isEqualTo(memberGrade.getRenewalPeriodStatusCode());
    }
}