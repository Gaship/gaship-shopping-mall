package shop.gaship.gashipshoppingmall.product.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;
import shop.gaship.gashipshoppingmall.error.FileDeleteFailureException;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

/**
 * 상품 생성 및 수정 이벤트 핸들러 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class ProductSaveUpdateEventHandler {
    private final FileUploadUtil fileUploadUtil;
    private final ElasticProductRepository elasticProductRepository;

    /**
     * 상품 생성 및 수정이 롤백되었을 때 업로드했던 파일을 삭제하는 이벤트 핸들 메서드입니다.
     *
     * @param event 상품 생성 및 수정 이벤트
     * @throws FileDeleteFailureException 파일 삭제에 오류가 발생하였을 때 에외를 던집니다.
     * @author 김보민
     */
    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleRollback(ProductSaveUpdateEvent event) {
        fileUploadUtil.cleanUpFiles(event.getImageLinks());
    }

    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCommit(ProductSaveUpdateEvent event) {
        Product savedProduct = event.getSavedProduct();
        elasticProductRepository.save(new ElasticProduct(
                savedProduct.getNo(), savedProduct.getName(), savedProduct.getCode()));
    }
}
