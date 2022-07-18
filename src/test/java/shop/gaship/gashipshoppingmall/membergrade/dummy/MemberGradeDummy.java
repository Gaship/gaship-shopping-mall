package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.entity.MemberGrade;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 회원등급 dummy.
 *
 * @author : 김세미
 * @since 1.0
 */
public class MemberGradeDummy {
    private MemberGradeDummy(){}

    public static MemberGrade dummy(MemberGradeAddRequestDto request, StatusCode renewalPeriod) {
        return MemberGrade.create(renewalPeriod, request);
    }

    public static MemberGrade defaultDummy(MemberGradeAddRequestDto request, StatusCode renewalPeriod){
        return MemberGrade.createDefault(renewalPeriod, request);
    }
}
