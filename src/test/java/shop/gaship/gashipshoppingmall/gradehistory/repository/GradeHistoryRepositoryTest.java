package shop.gaship.gashipshoppingmall.gradehistory.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryDummy;
import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryMemberDummy;
import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;
import shop.gaship.gashipshoppingmall.member.entity.Member;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 등급이력 Repository 테스트.
 *
 * @author : 김세미
 * @since 1.0
 */
@DataJpaTest
class GradeHistoryRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private GradeHistoryRepository gradeHistoryRepository;

    private Member memberDummy;

    @BeforeEach
    void setUp() {
        memberDummy = testMember();
    }

    @DisplayName("등급이력 등록/조회 테스트")
    @Test
    void saveAndFindGradeHistory(){
        GradeHistory newGradeHistory = testGradeHistory(memberDummy);
        testEntityManager.clear();

        Optional<GradeHistory> result = gradeHistoryRepository
                .findById(newGradeHistory.getNo());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getNo()).isEqualTo(newGradeHistory.getNo());
        assertThat(result.get().getGradeName()).isEqualTo("일반");
        assertThat(result.get().getTotalAmount()).isEqualTo(1_000_000L);
        assertThat(result.get().getAt()).isEqualTo(LocalDate.now());
    }

    @DisplayName("멤버를 통한 등급이력 다건 조회 테스트")
    @Test
    void getGradeHistoriesByMember(){
        GradeHistory gradeHistoryDummy = testGradeHistory(memberDummy);
        testGradeHistory(memberDummy);
        testGradeHistory(memberDummy);
        testEntityManager.clear();

        Page<GradeHistoryResponseDto> result = gradeHistoryRepository
                .getGradeHistoriesByMember(gradeHistoryDummy.getMember(), PageRequest.of(0, 1));

        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getGradeName())
                .isEqualTo(gradeHistoryDummy.getGradeName());
    }

    private Member testMember(){
        Member memberDummy = GradeHistoryMemberDummy.dummy();
        testEntityManager.persist(memberDummy.getMemberGrades().getRenewalPeriodStatusCode());
        testEntityManager.persist(memberDummy.getMemberStatusCodes());
        testEntityManager.persist(memberDummy.getMemberGrades());

        return testEntityManager.persist(memberDummy);
    }

    private GradeHistory testGradeHistory(Member memberDummy){
        GradeHistory gradeHistoryDummy = GradeHistoryDummy.dummy(memberDummy);

        return testEntityManager.persist(gradeHistoryDummy);
    }
}