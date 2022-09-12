package shop.gaship.gashipshoppingmall.productreview.service.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.aspact.exception.InvalidIdException;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.commonfile.repository.CommonFileRepository;
import shop.gaship.gashipshoppingmall.commonfile.service.CommonFileService;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;
import shop.gaship.gashipshoppingmall.member.entity.MembersRole;
import shop.gaship.gashipshoppingmall.member.exception.MemberNotFoundException;
import shop.gaship.gashipshoppingmall.member.repository.MemberRepository;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;
import shop.gaship.gashipshoppingmall.orderproduct.exception.OrderProductNotFoundException;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.product.exception.ProductNotFoundException;
import shop.gaship.gashipshoppingmall.product.repository.ProductRepository;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewViewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewExistsResponseDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;
import shop.gaship.gashipshoppingmall.productreview.entity.ProductReview;
import shop.gaship.gashipshoppingmall.productreview.event.ProductReviewDeleteEvent;
import shop.gaship.gashipshoppingmall.productreview.event.ProductReviewSaveUpdateEvent;
import shop.gaship.gashipshoppingmall.productreview.exception.ProductReviewNotFoundException;
import shop.gaship.gashipshoppingmall.productreview.repository.ProductReviewRepository;
import shop.gaship.gashipshoppingmall.productreview.service.ProductReviewService;

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
    private final ProductReviewRepository productReviewRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CommonFileRepository commonFileRepository;
    private final CommonFileService commonFileService;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * {@inheritDoc}
     *
     * @throws OrderProductNotFoundException 주문 상품이 없을 때 던질 예외
     */
    @Transactional
    @Override
    public void addProductReview(MultipartFile file, ProductReviewRequestDto createRequest,
                                 Integer requestMemberNo) {
        OrderProduct orderProduct = orderProductRepository
                .findById(createRequest.getOrderProductNo())
                .orElseThrow(OrderProductNotFoundException::new);

        checkMemberValid(requestMemberNo, orderProduct);

        ProductReview review = createProductReview(createRequest, orderProduct);

        if (Objects.nonNull(file)) {
            FileRequestDto fileRequest = commonFileService.uploadMultipartFile(file);
            applicationEventPublisher.publishEvent(new ProductReviewSaveUpdateEvent(fileRequest));
            review.addProductReviewImage(commonFileService.createCommonFile(fileRequest));
        }

        productReviewRepository.save(review);
    }

    /**
     * {@inheritDoc}
     *
     * @throws ProductReviewNotFoundException 상품평이 없을 때 던질 예외
     */
    @Transactional
    @Override
    public void modifyProductReview(MultipartFile file, ProductReviewRequestDto modifyRequest,
                                    Integer requestMemberNo) {
        ProductReview review = productReviewRepository.findById(modifyRequest.getOrderProductNo())
                .orElseThrow(ProductReviewNotFoundException::new);

        checkMemberValid(requestMemberNo, review.getOrderProduct());

        ProductReviewSaveUpdateEvent event =
                new ProductReviewSaveUpdateEvent(review.getReviewImages());
        applicationEventPublisher.publishEvent(event);
        review.removeAllProductReviewImages();

        if (Objects.nonNull(file)) {
            FileRequestDto fileRequest = commonFileService.uploadMultipartFile(file);
            event.setImagePath(fileRequest);
            review.addProductReviewImage(commonFileService.createCommonFile(fileRequest));
        }

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
    public void removeProductReview(Integer orderProductNo, Integer requestMemberNo,
                                    String requestRole) {
        ProductReview review = productReviewRepository.findById(orderProductNo)
                .orElseThrow(ProductReviewNotFoundException::new);

        if (!Objects.equals(requestRole, MembersRole.ROLE_ADMIN.getRole())) {
            checkMemberValid(requestMemberNo, review.getOrderProduct());
        }

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

    @Override
    public ProductReviewExistsResponseDto existsReview(Integer orderProductNo) {
        return new ProductReviewExistsResponseDto(productReviewRepository.findById(orderProductNo)
                .isPresent());
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
     * 상품평의 이미지경로를 찾아오는 메서드입니다.
     *
     * @param productReviews 이미지경로를 찾을 상품평
     */
    private void findFilePath(Page<ProductReviewResponseDto> productReviews) {
        productReviews.getContent().forEach(review -> review.getFilePaths()
                .addAll(commonFileRepository.findPaths(review.getOrderProductNo(),
                        ProductReview.SERVICE)));
    }

    private void checkMemberValid(Integer requestMemberNo, OrderProduct orderProduct) {
        if (!Objects.equals(requestMemberNo, orderProduct.getOrder().getMember().getMemberNo())) {
            throw new InvalidIdException();
        }
    }
}
