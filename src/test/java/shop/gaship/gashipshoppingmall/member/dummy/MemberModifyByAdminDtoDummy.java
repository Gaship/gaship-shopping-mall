package shop.gaship.gashipshoppingmall.member.dummy;

import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyByAdminDto;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
public class MemberModifyByAdminDtoDummy {
    private MemberModifyByAdminDtoDummy() {
    }

    public static MemberModifyByAdminDto dummy(){
        return MemberModifyByAdminDto.builder()
            .memberNo(1)
            .nickname("example")
            .status("Active")
            .build();
    }
}
