package shop.gaship.gashipshoppingmall.membergrade.dummy;

import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 상태코드 dummy.
 *
 * @author : 김세미
 * @since 1.0
 */
public class StatusCodeDummy {
    private StatusCodeDummy(){}

    public static StatusCode dummy() {
        return StatusCode.builder()
                .statusCodeName("1개월")
                .groupCodeName("갱신기간")
                .explanation("회원 등급 갱신기간 입니다.")
                .priority(1)
                .build();
    }
}
