package shop.gaship.gashipshoppingmall.membergrade.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberGradeRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MemberGradeRepository memberGradeRepository;

    private StatusCode renewalPeriod;
    private MemberGrade memberGrade;

    private MemberGradeAddRequestDto memberGradeAddRequestDto;
    @BeforeEach
    void setUp() {
        memberGradeAddRequestDto = MemberGradeDtoDummy.requestDummy("일반", 0L);
        renewalPeriod = StatusCodeDummy.dummy();
        memberGrade = MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod);
    }

    @Order(0)
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
    @Order(1)
    @Test
    void updateMemberGrade() {
        // given
        testEntityManager.persist(renewalPeriod);
        MemberGrade newMemberGrade = memberGradeRepository.save(memberGrade);

        // when
        newMemberGrade.modifyDetails(MemberGradeDtoDummy.modifyRequestDummy(1, "새싹", 1L));
        memberGradeRepository.saveAndFlush(newMemberGrade);
        testEntityManager.clear();

        // then
        Optional<MemberGrade> result = memberGradeRepository.findById(newMemberGrade.getNo());

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("새싹");
        assertThat(result.get().getAccumulateAmount()).isEqualTo(1L);
    }

    @Order(2)
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

    @Order(3)
    @Test
    void getMemberGradeBy_whenMemberGradeIsPresent() {
        // given
        testEntityManager.persist(renewalPeriod);
        MemberGrade newMemberGrade = memberGradeRepository.saveAndFlush(memberGrade);
        testEntityManager.clear();

        // when
        Optional<MemberGradeResponseDto> memberGradeDto =
                memberGradeRepository.getMemberGradeBy(newMemberGrade.getNo());

        // then
        assertThat(memberGradeDto).isPresent();
        assertThat(memberGradeDto.get().getName()).isEqualTo(newMemberGrade.getName());
        assertThat(memberGradeDto.get().getAccumulateAmount()).isEqualTo(newMemberGrade.getAccumulateAmount());
    }

    @Order(4)
    @Test
    void getMemberGradeBy_whenMemberGradIsEmpty() {
        // given
        Integer testMemberGradeNo = 2;

        // when
        Optional<MemberGradeResponseDto> memberGradeDto =
                memberGradeRepository.getMemberGradeBy(testMemberGradeNo);

        assertThat(memberGradeDto).isEmpty();
    }

    @Order(5)
    @Test
    void getMemberGrades() {
        // given
        testEntityManager.persist(renewalPeriod);
        memberGradeRepository.saveAndFlush(memberGrade);
        memberGradeAddRequestDto.setAccumulateAmount(1L);
        memberGradeRepository.saveAndFlush(MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod));
        memberGradeAddRequestDto.setAccumulateAmount(2L);
        memberGradeRepository.saveAndFlush(MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod));
        memberGradeAddRequestDto.setAccumulateAmount(3L);
        memberGradeRepository.saveAndFlush(MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod));
        memberGradeAddRequestDto.setAccumulateAmount(4L);
        memberGradeRepository.saveAndFlush(MemberGradeDummy.dummy(memberGradeAddRequestDto, renewalPeriod));
        testEntityManager.clear();

        Pageable pageable = PageRequest.of(1, 3);

        // when
        Page<MemberGradeResponseDto> result = memberGradeRepository.getMemberGrades(pageable);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(5);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("일반");
    }

    @Order(6)
    @DisplayName("isDefault 속성이 true 인 데이터가 있을때 existsByDefault 메서드 테스트")
    @Test
    void existsByDefaultIsTrue_whenDefaultIsExist(){
        // given
        MemberGrade defaultDummy = MemberGradeDummy
                .defaultDummy(memberGradeAddRequestDto, renewalPeriod);
        testEntityManager.persist(renewalPeriod);
        testEntityManager.persist(defaultDummy);
        testEntityManager.clear();

        // when
        boolean result = memberGradeRepository.existsByIsDefaultIsTrue();

        // then
        assertThat(result).isTrue();
    }

    @Order(7)
    @DisplayName("isDefault 속성이 true 인 데이터가 없을때 existsByDefault 메서드 테스트")
    @Test
    void existsByDefaultIsTrue_whenDefaultIsNotExist(){
        // given
        testEntityManager.persist(renewalPeriod);
        testEntityManager.persist(memberGrade);
        testEntityManager.clear();

        // when
        boolean result = memberGradeRepository.existsByIsDefaultIsTrue();

        // then
        assertThat(result).isFalse();
    }

    @Order(8)
    @DisplayName("기준누적금액이 동일한 회원등급이 이미 존재하는 경우")
    @Test
    void existsByAccumulateAmountEquals_whenIsOverlap(){
        // given
        Long dummyAccumulateAmount = 0L;
        testEntityManager.persist(renewalPeriod);
        testEntityManager.persist(memberGrade);
        testEntityManager.clear();

        // when
        boolean result = memberGradeRepository.existsByAccumulateAmountEquals(dummyAccumulateAmount);

        // then
        assertThat(result).isTrue();
    }

    @Order(9)
    @DisplayName("기준누적금액이 동일한 회원등급이 존재하지 않는 경우")
    @Test
    void existsByAccumulateAmountEquals_whenIsNotOverlap(){
        // given
        Long dummyAccumulateAmount = 100_000_000L;
        testEntityManager.persist(renewalPeriod);
        testEntityManager.persist(memberGrade);
        testEntityManager.clear();

        // when
        boolean result = memberGradeRepository.existsByAccumulateAmountEquals(dummyAccumulateAmount);

        // then
        assertThat(result).isFalse();
    }
}