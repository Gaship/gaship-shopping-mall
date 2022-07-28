package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.gradehistory.dummy.GradeHistoryDtoDummy;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.RenewalMemberGradeRequestDto;

/**
 * 회원 등급 갱신 요청 dto dummy data.
 *
 * @author : 김세미
 * @since 1.0
 */
public class RenewalMemberGradeRequestDummy {
    private RenewalMemberGradeRequestDummy(){}

    public static RenewalMemberGradeRequestDto dummy(){
        RenewalMemberGradeRequestDto dummy = new RenewalMemberGradeRequestDto();
        dummy.setGradeHistoryAddRequestDto(GradeHistoryDtoDummy.addRequestDummy());
        dummy.setAdvancementMemberRequestDto(AdvancementMemberRequestDummy.dummy());

        return dummy;
    }
}
