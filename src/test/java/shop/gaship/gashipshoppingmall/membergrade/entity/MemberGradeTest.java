package shop.gaship.gashipshoppingmall.membergrade.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.entity
 * fileName       : MemberGradeTest
 * author         : Semi Kim
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14        Semi Kim       최초 생성
 */
class MemberGradeTest {

    private StatusCode renewalPeriod;
    private MemberGradeRequestDto memberGradeRequestDto;

    @BeforeEach
    void setUp() {
        renewalPeriod = StatusCodeDummy.dummy();
        memberGradeRequestDto = MemberGradeDtoDummy.requestDummy(1,"일반", 0L);
    }

    @DisplayName("회원등급 getNo 메서드 테스트")
    @Test
    void getNo(){
        // given
        MemberGrade memberGrade = MemberGradeDummy
                .dummy(memberGradeRequestDto, renewalPeriod);
        ReflectionTestUtils.setField(memberGrade, "no", 1);

        // when
        Integer result = memberGrade.getNo();

        // then
        assertThat(result).isOne();
    }

    @DisplayName("회원등급 getRenewalPeriodStatusCode 메서드 테스트")
    @Test
    void getRenewalPeriodStatusCode(){
        // given
        MemberGrade memberGrade = MemberGradeDummy
                .dummy(memberGradeRequestDto, renewalPeriod);

        // when
        StatusCode result = memberGrade.getRenewalPeriodStatusCode();

        // then
        assertThat(result).isEqualTo(renewalPeriod);
    }

    @DisplayName("기본 회원등급 생성 메서드 테스트")
    @Test
    void createDefault(){
        // when
        MemberGrade result = MemberGrade.createDefault(renewalPeriod, memberGradeRequestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("일반");
        assertThat(result.getAccumulateAmount()).isZero();
        assertThat(result.isDefault()).isTrue();
    }

    @DisplayName("기본 이외의 회원등급 생성 메서드 테스트")
    @Test
    void create(){
        // when
        String dummyName = "VIP";
        Long dummyAccumulateAmount = 100_000_000L;
        MemberGrade result = MemberGrade.create(renewalPeriod,
                MemberGradeDtoDummy.requestDummy(1, dummyName, dummyAccumulateAmount));

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(dummyName);
        assertThat(result.getAccumulateAmount()).isEqualTo(dummyAccumulateAmount);
    }

    @DisplayName("회원등급 이름/기준누적금액 수정 메서드 테스트")
    @Test
    void modifyDetails(){
        // given
        String modifyNameData = "새싹";
        Long modifyAccumulateAmountData = 1L;

        MemberGrade memberGrade = MemberGradeDummy
                .dummy(memberGradeRequestDto, renewalPeriod);
        MemberGradeModifyRequestDto modifyRequestDto = MemberGradeDtoDummy
                .modifyRequestDummy(1, modifyNameData, modifyAccumulateAmountData);

        // when
        memberGrade.modifyDetails(modifyRequestDto);

        // then
        assertThat(memberGrade.getName()).isEqualTo(modifyNameData);
        assertThat(memberGrade.getAccumulateAmount()).isEqualTo(modifyAccumulateAmountData);
    }
}
