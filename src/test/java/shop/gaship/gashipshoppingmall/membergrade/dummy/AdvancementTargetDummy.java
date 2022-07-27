package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;

/**
 * advancementTargetDto dummy 데이터.
 *
 * @author : 김세미
 * @since 1.0
 */
public class AdvancementTargetDummy {
    private AdvancementTargetDummy(){}

    public static AdvancementTargetResponseDto dummy(){
        AdvancementTargetResponseDto dummy = new AdvancementTargetResponseDto();
        dummy.setMemberNo(1);

        return dummy;
    }
}
