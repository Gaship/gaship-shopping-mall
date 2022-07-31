package shop.gaship.gashipshoppingmall.addresslocal.dummy;

import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.dummy
 * fileName       : ModifyAddressRequestDtoDummy
 * author         : 유호철
 * date           : 2022/07/14
 * description    : test를 위한 더미 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
public class ModifyAddressRequestDtoDummy {
    private ModifyAddressRequestDtoDummy() {

    }

    public static ModifyAddressRequestDto dummy() {
        return new ModifyAddressRequestDto(1, true);
    }
}
