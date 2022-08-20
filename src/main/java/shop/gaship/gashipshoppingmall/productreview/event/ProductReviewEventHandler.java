package shop.gaship.gashipshoppingmall.productreview.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.file.service.FileService;

/**
 * 상품평 이벤트 핸들러입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductReviewEventHandler {
    private final FileService fileService;

    /**
     * 상품평 생성, 수정 이벤트 롤백 핸들 메서드입니다.
     * 상품평 생성, 수정이 실패할 시 업로드했던 이미지들을 삭제합니다.
     *
     * @param event 상품평 생성, 수정 이벤트
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleSaveUpdateRollback(ProductReviewSaveUpdateEvent event) {
        fileService.delete(event.getImagePath().getPath());
    }

    /**
     * 상품평 생성, 수정 커밋 핸들 메서드입니다.
     * 상품평 수정이 정상적으로 커밋될 시 로컬에 업로드되었던 이전 파일들을 삭제합니다.
     *
     * @param event 상품평 생성, 수정 이벤트
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSaveUpdateCommit(ProductReviewSaveUpdateEvent event) {
        event.getBeforeImages().stream().map(CommonFile::getPath)
                .forEach(fileService::delete);
    }

    /**
     * 상품평 삭제 이벤트 핸들 메서드입니다.
     * 상품평 삭제가 완료되면 업로드 되어있는 상품평 이미지들을 삭제합니다.
     *
     * @param event 상품평 삭제 이벤트
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleDeleteCommit(ProductReviewDeleteEvent event) {
        event.getImagePaths().forEach(fileService::delete);
    }
}
