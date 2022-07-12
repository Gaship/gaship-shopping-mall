package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.dummy
 * fileName       : StatusCodeDummy
 * author         : Semi Kim
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/13        Semi Kim       최초 생성
 */
public class StatusCodeDummy {
    private StatusCodeDummy(){}

    public static StatusCode dummy() {
        return StatusCode.builder()
                .statusCodeName("1개월")
                .groupCodeName("갱신기간")
                .explanation("회원 등급 갱신기간 입니다.")
                .build();
    }
}
