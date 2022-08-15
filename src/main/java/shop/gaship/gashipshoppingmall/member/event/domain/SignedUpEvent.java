package shop.gaship.gashipshoppingmall.member.event.domain;

import lombok.RequiredArgsConstructor;

/**
 * @author : 최겸준
 * @since 1.0
 */
@RequiredArgsConstructor
public class SignedUpEvent {
    private final Integer recommendMemberNo;

    public Integer getRecommendMemberNo() {
        return recommendMemberNo;
    }
}
