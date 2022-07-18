package shop.gaship.gashipshoppingmall.addressLocal.dummy;

import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.dummy
 * fileName       : GetAddressLocalResponseDtoDummy
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
public class GetAddressLocalResponseDtoDummy {
    private GetAddressLocalResponseDtoDummy() {

    }

    public static GetAddressLocalResponseDto dummy() {
        return new GetAddressLocalResponseDto("마산턱별시", "test");
    }
}
