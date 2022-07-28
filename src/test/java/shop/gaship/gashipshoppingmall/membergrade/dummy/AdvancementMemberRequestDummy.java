package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.membergrade.dto.request.AdvancementMemberRequestDto;

import java.time.LocalDate;

/**
 * 회원의 승급 요청 dto dummy data.
 *
 * @author : 김세미
 * @since 1.0
 */
public class AdvancementMemberRequestDummy {
    private AdvancementMemberRequestDummy(){}

    public static AdvancementMemberRequestDto dummy(){
        AdvancementMemberRequestDto dummy = new AdvancementMemberRequestDto();
        dummy.setMemberNo(1);
        dummy.setMemberGradeNo(1);
        dummy.setNextRenewalGradeDate("2022-07-27");

        return dummy;
    }
}
