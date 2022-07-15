package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeRequestDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.dummy
 * fileName       : MemberGradeDtoDummy
 * author         : Semi Kim
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13        Semi Kim       최초 생성
 */
public class MemberGradeDtoDummy {
    private MemberGradeDtoDummy(){}

    public static MemberGradeRequestDto requestDummy(String name, Long accumulateAmount) {
        MemberGradeRequestDto memberGradeRequestDto = new MemberGradeRequestDto();
        memberGradeRequestDto.setName(name);
        memberGradeRequestDto.setAccumulateAmount(accumulateAmount);

        return memberGradeRequestDto;
    }

    public static MemberGradeResponseDto responseDummy(String name, Long accumulateAmount, String renewalPeriodStatusCode) {
        MemberGradeResponseDto testMemberGradeResponseDto = new MemberGradeResponseDto();
        testMemberGradeResponseDto.setName(name);
        testMemberGradeResponseDto.setAccumulateAmount(accumulateAmount);
        testMemberGradeResponseDto.setRenewalPeriodStatusCode(renewalPeriodStatusCode);

        return testMemberGradeResponseDto;
    }
}