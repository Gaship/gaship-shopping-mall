package shop.gaship.gashipshoppingmall.product.event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;
import shop.gaship.gashipshoppingmall.error.FileDeleteFailureException;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.storage.service.ObjectStorageService;
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
    private final ObjectStorageService objectStorageService;
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
//        fileUploadUtil.cleanUpFiles(event.getImageLinks());
        event.getImageLinks().stream().map(link -> link.substring(link.lastIndexOf("/") + 1))
                .forEach(objectStorageService::deleteObject);
    }

    /**
     * 상품 생성, 수정 커밋 시 이벤트 핸들 메서드입니다.
     * 수정 전 이미지파일 삭제합니다, 상품을 엘라스틱 레퍼지토리에 저장합니다.
     *
     * @param event 상품 생성 및 수정 이벤트
     */
    @Transactional
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCommit(ProductSaveUpdateEvent event) {
//        fileUploadUtil.cleanUpFiles(event.getBeforeImages().stream()
//                .map(CommonFile::getPath)
//                .collect(Collectors.toList()));
        event.getBeforeImages().stream().map(CommonFile::getOriginalName)
                .forEach(objectStorageService::deleteObject);

        Product savedProduct = event.getSavedProduct();
        elasticProductRepository.save(new ElasticProduct(
                savedProduct.getNo(), savedProduct.getName(), savedProduct.getCode()));
    }
}
