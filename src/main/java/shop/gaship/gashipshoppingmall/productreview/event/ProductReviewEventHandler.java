package shop.gaship.gashipshoppingmall.productreview.event;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

/**
 * 상품평 save 이벤트 핸들러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductReviewEventHandler {
    private final FileUploadUtil fileUploadUtil;

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleSaveEvent(ProductReviewSaveEvent event) {
        fileUploadUtil.deleteFiles(List.of(event.getImagePath()));
    }

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleDeleteEvent(ProductReviewDeleteEvent event) {
        fileUploadUtil.deleteFiles(List.of(event.getImagePath()));
    }
}
