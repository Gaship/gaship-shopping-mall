package shop.gaship.gashipshoppingmall.member.event.domain;

import lombok.RequiredArgsConstructor;

/**
 * Event 발생시에 handler에서 사용되어야 할 정보를 저장하는 클래스입니다.
 *
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
