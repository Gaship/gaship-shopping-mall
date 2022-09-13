package shop.gaship.gashipshoppingmall.member.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.member.adapter.MemberAdapter;
import shop.gaship.gashipshoppingmall.member.event.domain.SignedUpEvent;

/**
 * 이벤트를 받아서 핸들링하는 클래스입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SignedUpEventHandler {
    private final MemberAdapter memberAdapter;

    /**
     * 쿠폰서버와 연결되어있는 Adapter에게 쿠폰생성발급 요청을 위임합니다.
     *
     * @param event 요청시 필요한 추천인번호를 들고있는 클래스입니다.
     */
    @Async("basicThreadPoolTaskExecutor")
    @TransactionalEventListener(
        classes = SignedUpEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleCommit(SignedUpEvent event) {
        memberAdapter.requestSendRecommendMemberCouponGenerationIssue(event.getRecommendMemberNo());
    }
}
