package shop.gaship.gashipshoppingmall.product.event;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;
import shop.gaship.gashipshoppingmall.error.FileDeleteFailureException;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;
import shop.gaship.gashipshoppingmall.file.service.FileService;

/**
 * 상품 생성 및 수정 이벤트 핸들러 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductSaveUpdateEventHandler {
    private final FileService fileService;

    /**
     * 상품 생성 및 수정이 롤백되었을 때 업로드했던 파일을 삭제하는 이벤트 핸들 메서드입니다.
     *
     * @param event 상품 생성 및 수정 이벤트
     * @throws FileDeleteFailureException 파일 삭제에 오류가 발생하였을 때 에외를 던집니다.
     * @author 김보민
     */
    @Async("basicThreadPoolTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleRollback(ProductSaveUpdateEvent event) {
        event.getImageLinks().stream().map(FileRequestDto::getPath).forEach(fileService::delete);
    }

    /**
     * 상품 생성, 수정 커밋 시 이벤트 핸들 메서드입니다.
     * 수정 전 이미지파일 삭제합니다.
     *
     * @param event 상품 생성 및 수정 이벤트
     */
    @Async("basicThreadPoolTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCommit(ProductSaveUpdateEvent event) {
        event.getBeforeImages().stream().map(CommonFile::getPath)
                .forEach(fileService::delete);
    }
}
