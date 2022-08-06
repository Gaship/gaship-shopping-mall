package shop.gaship.gashipshoppingmall.productreview.event;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

/**
 * 상품평 이벤트 핸들러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductReviewEventHandler {
    private final FileUploadUtil fileUploadUtil;

    /**
     * 상품평 생성 이벤트 핸들 메서드입니다.
     * 상품평 생성이 실패할 시 업로드했던 이미지들을 삭제합니다.
     *
     * @param event 상품평 생성 이벤트
     */
    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleSaveEvent(ProductReviewSaveEvent event) {
        fileUploadUtil.cleanUpFiles(List.of(event.getImagePath()));
    }

    /**
     * 상품평 삭제 이벤트 핸들 메서드입니다.
     * 상품평 삭제가 완료되면 업로드 되어있는 상품평 이미지들을 삭제합니다.
     *
     * @param event 상품평 삭제 이벤트
     */
    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleDeleteEvent(ProductReviewDeleteEvent event) {
        fileUploadUtil.cleanUpFiles(List.of(event.getImagePath()));
    }
}
