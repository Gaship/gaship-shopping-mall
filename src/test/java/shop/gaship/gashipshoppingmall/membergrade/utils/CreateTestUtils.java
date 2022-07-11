package shop.gaship.gashipshoppingmall.membergrade.utils;

import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.membergrade.request.MemberGradeRequest;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.utils
 * fileName       : CreateTestUtils
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
public class CreateTestUtils {
    private CreateTestUtils() {
    }

    public static MemberGradeRequest createTestMemberGradeRequest(String name, Long accumulateAmount) {
        MemberGradeRequest memberGradeRequest = new MemberGradeRequest();
        memberGradeRequest.setName(name);
        memberGradeRequest.setAccumulateAmount(accumulateAmount);

        return memberGradeRequest;
    }

    public static StatusCode createTestStatusCode() {
        return StatusCode.builder()
                .statusCodeName("1개월")
                .groupCodeName("갱신기간")
                .explanation("회원 등급 갱신기간 입니다.")
                .build();
    }

    public static MemberGrade createTestMemberGrade(MemberGradeRequest request, StatusCode renewalPeriod) {
        return MemberGrade.builder()
                .renewalPeriod(renewalPeriod)
                .name(request.getName())
                .accumulateAmount(request.getAccumulateAmount())
                .build();
    }

    public static MemberGradeDto createTestMemberGradeDto(String name, Long accumulateAmount, String renewalPeriodStatusCode) {
        MemberGradeDto testMemberGradeDto = new MemberGradeDto();
        testMemberGradeDto.setName(name);
        testMemberGradeDto.setAccumulateAmount(accumulateAmount);
        testMemberGradeDto.setRenewalPeriodStatusCode(renewalPeriodStatusCode);

        return testMemberGradeDto;
    }
}
