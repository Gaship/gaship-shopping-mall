package shop.gaship.gashipshoppingmall.member.dummy;

import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * @author 최정우
 * @since 1.0
 */
public class StatusCodeDummy {
    private StatusCodeDummy(){}

    public static StatusCode dummy(){
        return StatusCode.builder()
                .statusCodeName(MemberStatus.ACTIVATION.getValue())
                .groupCodeName(MemberStatus.GROUP)
                .priority(1)
                .explanation("홰언샹테")
                .build();
    }
}
