package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;

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

    public static MemberGradeAddRequestDto requestDummy(String name, Long accumulateAmount) {
        MemberGradeAddRequestDto dummy = new MemberGradeAddRequestDto();
        dummy.setName(name);
        dummy.setAccumulateAmount(accumulateAmount);
        dummy.setIsDefault(false);

        return dummy;
    }

    public static MemberGradeModifyRequestDto modifyRequestDummy(Integer no, String name, Long accumulateAmount){
        MemberGradeModifyRequestDto dummy = new MemberGradeModifyRequestDto();
        dummy.setNo(no);
        dummy.setName(name);
        dummy.setAccumulateAmount(accumulateAmount);
        dummy.setIsDefault(false);

        return dummy;
    }

    public static MemberGradeResponseDto responseDummy(String name, Long accumulateAmount, String renewalPeriodStatusCode) {
        MemberGradeResponseDto testMemberGradeResponseDto = new MemberGradeResponseDto();
        testMemberGradeResponseDto.setName(name);
        testMemberGradeResponseDto.setAccumulateAmount(accumulateAmount);
        testMemberGradeResponseDto.setRenewalPeriodStatusCode(renewalPeriodStatusCode);

        return testMemberGradeResponseDto;
    }
}
