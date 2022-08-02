package shop.gaship.gashipshoppingmall.productreview.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewViewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
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
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
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

    @Override
    public ProductReviewResponseDto findReview(Integer orderProductNo) {
        if (orderProductRepository.findById(orderProductNo).isEmpty()) {
            throw new OrderProductNotFoundException();
        }

        return productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .orderProductNo(orderProductNo)
                        .build()
                ).getContent().get(0);
    }

    @Override
    public Page<ProductReviewResponseDto> findReviews(Pageable pageable) {
        return productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .pageable(pageable)
                .build());
    }

    @Override
    public Page<ProductReviewResponseDto> findReviewsByProductNo(Integer productNo,
                                                                 Pageable pageable) {
        if (productRepository.findById(productNo).isEmpty()) {
            throw new ProductNotFoundException();
        }

        return productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .productNo(productNo)
                        .pageable(pageable)
                .build());
    }

    @Override
    public Page<ProductReviewResponseDto> findReviewsByMemberNo(Integer memberNo,
                                                                Pageable pageable) {
        if (memberRepository.findById(memberNo).isEmpty()) {
            throw new MemberNotFoundException();
        }

        return productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .memberNo(memberNo)
                        .pageable(pageable)
                .build());
    }

    private ProductReview createProductReview(ProductReviewRequestDto createRequest) {
        return ProductReview.builder()
                .orderProductNo(createRequest.getOrderProductNo())
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .starScore(createRequest.getStarScore())
                .registerDatetime(LocalDateTime.now())
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
