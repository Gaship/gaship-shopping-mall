package shop.gaship.gashipshoppingmall.productreview.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
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
    private final CommonFileRepository fileRepository;
    private final CommonFileService fileService;
    private final FileUploadUtil fileUploadUtil;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * {@inheritDoc}
     *
     * @throws OrderProductNotFoundException 주문 상품이 없을 때 던질 예외
     */
    @Transactional
    @Override
    public void addProductReview(MultipartFile file, ProductReviewRequestDto createRequest) {
        OrderProduct orderProduct = orderProductRepository
                .findById(createRequest.getOrderProductNo())
                .orElseThrow(OrderProductNotFoundException::new);

        ProductReview review = createProductReview(createRequest, orderProduct);
        uploadProductReviewImage(review, file);

        productReviewRepository.save(review);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ProductReviewNotFoundException 상품평이 없을 때 던질 예외
     */
    @Transactional
    @Override
    public void modifyProductReview(MultipartFile file, ProductReviewRequestDto modifyRequest) {
        ProductReview review = productReviewRepository.findById(modifyRequest.getOrderProductNo())
                .orElseThrow(ProductReviewNotFoundException::new);

        //로컬에 업로드되어있던 이미지 파일들 삭제
        fileUploadUtil.cleanUpFiles(review.getReviewImages().stream()
                .map(CommonFile::getPath)
                .collect(Collectors.toList()));

        //db 파일 데이터 삭제
        review.removeAllProductReviewImages();

        uploadProductReviewImage(review, file);

        review.updateProductReview(modifyRequest.getTitle(), modifyRequest.getContent(),
                modifyRequest.getStarScore());
    }

    /**
     * {@inheritDoc}
     *
     * @throws ProductReviewNotFoundException 상품평이 없을 때 던질 예외
     */
    @Transactional
    @Override
    public void removeProductReview(Integer orderProductNo) {
        ProductReview review = productReviewRepository.findById(orderProductNo)
                .orElseThrow(ProductReviewNotFoundException::new);

        productReviewRepository.deleteById(orderProductNo);

        applicationEventPublisher.publishEvent(
                new ProductReviewDeleteEvent(review.getReviewImages().stream()
                        .map(CommonFile::getPath)
                        .collect(Collectors.toList())));
    }

    /**
     * {@inheritDoc}
     *
     * @throws OrderProductNotFoundException 주문 상품이 없을 때 던질 예외
     * @throws ProductReviewNotFoundException 상품평이 없을 때 던질 예외
     */
    @Transactional(readOnly = true)
    @Override
    public ProductReviewResponseDto findReview(Integer orderProductNo) {
        if (orderProductRepository.findById(orderProductNo).isEmpty()) {
            throw new OrderProductNotFoundException();
        }

        Page<ProductReviewResponseDto> reviewResponseDtos =
                productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .orderProductNo(orderProductNo)
                        .build());
        findFilePath(reviewResponseDtos);

        return reviewResponseDtos.getContent().stream()
                .findFirst()
                .orElseThrow(ProductReviewNotFoundException::new);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ProductReviewResponseDto> findReviews(Pageable pageable) {
        Page<ProductReviewResponseDto> reviewResponseDtos =
                productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .pageable(pageable)
                        .build());
        findFilePath(reviewResponseDtos);
        return reviewResponseDtos;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ProductNotFoundException 상품이 없을 때 던질 예외
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ProductReviewResponseDto> findReviewsByProductNo(Integer productNo,
                                                                 Pageable pageable) {
        if (productRepository.findById(productNo).isEmpty()) {
            throw new ProductNotFoundException();
        }

        Page<ProductReviewResponseDto> reviewResponseDtos =
                productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .productNo(productNo)
                        .pageable(pageable)
                        .build());
        findFilePath(reviewResponseDtos);
        return reviewResponseDtos;
    }

    /**
     * {@inheritDoc}
     *
     * @throws MemberNotFoundException 회원이 없을 때 던질 예외
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ProductReviewResponseDto> findReviewsByMemberNo(Integer memberNo,
                                                                Pageable pageable) {
        if (memberRepository.findById(memberNo).isEmpty()) {
            throw new MemberNotFoundException();
        }

        Page<ProductReviewResponseDto> reviewResponseDtos =
                productReviewRepository.findProductReviews(ProductReviewViewRequestDto.builder()
                        .memberNo(memberNo)
                        .pageable(pageable)
                        .build());
        findFilePath(reviewResponseDtos);
        return reviewResponseDtos;
    }

    /**
     * 생성 요청 dto를 엔티티로 반환합니다.
     *
     * @param createRequest 상품평 생성 요청 dto
     * @param orderProduct 상품평의 주문상품
     * @return productReview 상품평
     */
    private ProductReview createProductReview(ProductReviewRequestDto createRequest,
                                              OrderProduct orderProduct) {
        return ProductReview.builder()
                .orderProduct(orderProduct)
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .starScore(createRequest.getStarScore())
                .registerDatetime(LocalDateTime.now())
                .build();
    }

    /**
     * 상품평 이미지가 있을 경우 해당 이미지를 업로드하는 메서드입니다.
     *
     * @param review 상품평
     * @param file 이미지 파일
     */
    private void uploadProductReviewImage(ProductReview review, MultipartFile file) {
        if (Objects.nonNull(file)) {
            String imagePath = fileUploadUtil.uploadFile(REVIEW_DIR, List.of(file)).get(0);
            applicationEventPublisher.publishEvent(new ProductReviewSaveEvent(imagePath));
            review.addProductReviewImage(fileService.createCommonFile(imagePath));
        }
    }

    private void findFilePath(Page<ProductReviewResponseDto> productReviews) {
        productReviews.getContent().forEach(review -> review.getFilePaths()
                .addAll(fileRepository.findPaths(review.getOrderProductNo(),
                        ProductReview.SERVICE)));
    }
}
