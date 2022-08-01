package shop.gaship.gashipshoppingmall.productreview.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.event.ProductReviewDeleteEvent;
import shop.gaship.gashipshoppingmall.productreview.event.ProductReviewSaveEvent;
import shop.gaship.gashipshoppingmall.productreview.exception.ProductReviewNotFoundException;
import shop.gaship.gashipshoppingmall.productreview.repository.ProductReviewRepository;
import shop.gaship.gashipshoppingmall.productreview.service.ProductReviewService;
import shop.gaship.gashipshoppingmall.util.FileUploadUtil;

/**
 * 상품평 서비스 구현체입니다.
 *
 * @author : 김보민
 * @see shop.gaship.gashipshoppingmall.productreview.service.ProductReviewService
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {
    private static final String REVIEW_DIR = File.separator + "reviews";
    private final ProductReviewRepository productReviewRepository;
    private final OrderProductRepository orderProductRepository;
    private final FileUploadUtil fileUploadUtil;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    @Override
    public void addProductReview(MultipartFile file, ProductReviewRequestDto createRequest) {
        if (orderProductRepository.findById(createRequest.getOrderProductNo()).isEmpty()) {
            throw new OrderProductNotFoundException();
        }

        ProductReview review = createProductReview(createRequest);
        uploadProductReviewImage(review, file);

        productReviewRepository.save(review);
    }

    @Transactional
    @Override
    public void modifyProductReview(MultipartFile file, ProductReviewRequestDto modifyRequest) {
        ProductReview review = productReviewRepository.findById(modifyRequest.getOrderProductNo())
                .orElseThrow(ProductReviewNotFoundException::new);

        if (!Objects.isNull(review.getImagePath())) {
            fileUploadUtil.cleanUpFiles(List.of(review.getImagePath()));
        }

        uploadProductReviewImage(review, file);

        review.updateProductReview(modifyRequest.getTitle(), modifyRequest.getContent(),
                modifyRequest.getStarScore());
    }

    @Transactional
    @Override
    public void removeProductReview(Integer orderProductNo) {
        ProductReview review = productReviewRepository.findById(orderProductNo)
                .orElseThrow(ProductReviewNotFoundException::new);

        productReviewRepository.deleteById(orderProductNo);

        applicationEventPublisher.publishEvent(new ProductReviewDeleteEvent(review.getImagePath()));
    }

    private ProductReview createProductReview(ProductReviewRequestDto createRequest) {
        return ProductReview.builder()
                .orderProductNo(createRequest.getOrderProductNo())
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .starScore(createRequest.getStarScore())
                .build();
    }

    private void uploadProductReviewImage(ProductReview review, MultipartFile file) {
        if (!Objects.isNull(file)) {
            String imagePath = fileUploadUtil.uploadFile(REVIEW_DIR, List.of(file)).get(0);
            review.updateImagePath(imagePath);
            applicationEventPublisher.publishEvent(new ProductReviewSaveEvent(imagePath));
        }
    }
}
