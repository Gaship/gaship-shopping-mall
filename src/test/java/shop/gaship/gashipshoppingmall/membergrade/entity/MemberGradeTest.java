package shop.gaship.gashipshoppingmall.membergrade.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.MemberGradeDummy;
import shop.gaship.gashipshoppingmall.membergrade.dummy.StatusCodeDummy;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 회원등급 entity test.
 *
 * @author : 김세미
 * @since 1.0
 */
class MemberGradeTest {

    private StatusCode renewalPeriod;
    private MemberGradeAddRequestDto memberGradeAddRequestDto;

    @BeforeEach
    void setUp() {
        renewalPeriod = StatusCodeDummy.dummy();
        memberGradeAddRequestDto = MemberGradeDtoDummy.requestDummy("일반", 0L);
    }

    @DisplayName("회원등급 getNo 메서드 테스트")
    @Test
    void getNo(){
        MemberGrade memberGrade = MemberGradeDummy
                .dummy(memberGradeAddRequestDto, renewalPeriod);
        ReflectionTestUtils.setField(memberGrade, "no", 1);

        Integer result = memberGrade.getNo();

        assertThat(result).isOne();
    }

    @DisplayName("회원등급 getRenewalPeriodStatusCode 메서드 테스트")
    @Test
    void getRenewalPeriodStatusCode(){
        MemberGrade memberGrade = MemberGradeDummy
                .dummy(memberGradeAddRequestDto, renewalPeriod);

        StatusCode result = memberGrade.getRenewalPeriodStatusCode();

        assertThat(result).isEqualTo(renewalPeriod);
    }

    @DisplayName("기본 회원등급 생성 메서드 테스트")
    @Test
    void createDefault(){
        MemberGrade result = MemberGrade.createDefault(renewalPeriod, memberGradeAddRequestDto);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("일반");
        assertThat(result.getAccumulateAmount()).isZero();
        assertThat(result.isDefault()).isTrue();
    }

    @DisplayName("기본 이외의 회원등급 생성 메서드 테스트")
    @Test
    void create(){
        String dummyName = "VIP";
        Long dummyAccumulateAmount = 100_000_000L;
        MemberGrade result = MemberGrade.create(renewalPeriod,
                MemberGradeDtoDummy.requestDummy(dummyName, dummyAccumulateAmount));

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(dummyName);
        assertThat(result.getAccumulateAmount()).isEqualTo(dummyAccumulateAmount);
    }

    @DisplayName("회원등급 이름/기준누적금액 수정 메서드 테스트")
    @Test
    void modifyDetails(){
        String modifyNameData = "새싹";
        Long modifyAccumulateAmountData = 1L;

        MemberGrade memberGrade = MemberGradeDummy
                .dummy(memberGradeAddRequestDto, renewalPeriod);
        MemberGradeModifyRequestDto modifyRequestDto = MemberGradeDtoDummy
                .modifyRequestDummy(1, modifyNameData, modifyAccumulateAmountData);

        memberGrade.modifyDetails(modifyRequestDto);

        assertThat(memberGrade.getName()).isEqualTo(modifyNameData);
        assertThat(memberGrade.getAccumulateAmount()).isEqualTo(modifyAccumulateAmountData);
    }
}
