package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.dummy
 * fileName       : MemberGradeDummy
 * author         : Semi Kim
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13        Semi Kim       최초 생성
 */
public class MemberGradeDummy {
    private MemberGradeDummy(){}

    public static MemberGrade dummy(MemberGradeRequestDto request, StatusCode renewalPeriod) {
        return MemberGrade.create(renewalPeriod, request);
    }

    public static MemberGrade defaultDummy(MemberGradeRequestDto request, StatusCode renewalPeriod){
        return MemberGrade.createDefault(renewalPeriod, request);
    }
}
