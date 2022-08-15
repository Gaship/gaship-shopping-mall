package shop.gaship.gashipshoppingmall.member.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.member.adapter.MemberAdapter;
import shop.gaship.gashipshoppingmall.member.event.domain.SignedUpEvent;

/**
 * @author : 최겸준
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class SignedUpEventHandler {
    private final MemberAdapter memberAdapter;

    @Async
    @TransactionalEventListener(
        classes = SignedUpEvent.class,
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleCommit(SignedUpEvent event) {
        memberAdapter.requestSendRecommendMemberCouponGenerationIssue(event.getRecommendMemberNo());
    }
}
