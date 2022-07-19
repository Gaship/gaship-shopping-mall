package shop.gaship.gashipshoppingmall.gradehistory.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryDummy;
import shop.gaship.gashipshoppingmall.gradehistory.entity.GradeHistory;

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

    private GradeHistory gradeHistoryDummy;

    @BeforeEach
    void setUp() {
        gradeHistoryDummy = GradeHistoryDummy.dummy();
    }

    @DisplayName("등급이력 등록/조회 테스트")
    @Test
    void saveAndFindGradeHistory(){
        testEntityManager.persist(gradeHistoryDummy.getMember().getMemberGrades().getRenewalPeriodStatusCode());
        testEntityManager.persist(gradeHistoryDummy.getMember().getMemberStatusCodes());
        testEntityManager.persist(gradeHistoryDummy.getMember().getMemberGrades());
        testEntityManager.persist(gradeHistoryDummy.getMember());
        GradeHistory newGradeHistory = testEntityManager.persist(gradeHistoryDummy);
        testEntityManager.clear();

        Optional<GradeHistory> result = gradeHistoryRepository
                .findById(newGradeHistory.getNo());

        assertThat(result).isNotEmpty();
        assertThat(result.get().getNo()).isEqualTo(newGradeHistory.getNo());
        assertThat(result.get().getGradeName()).isEqualTo("일반");
        assertThat(result.get().getTotalAmount()).isEqualTo(1_000_000L);
    }
}