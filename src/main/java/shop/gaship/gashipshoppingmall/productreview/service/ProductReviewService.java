package shop.gaship.gashipshoppingmall.productreview.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.productreview.dto.request.ProductReviewRequestDto;
import shop.gaship.gashipshoppingmall.productreview.dto.response.ProductReviewResponseDto;

/**
 * 상품평 서비스 인터페이스입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductReviewService {
    /**
     * 상품평 추가 서비스 메서드입니다.
     *
     * @param file          상품평 이미지
     * @param createRequest 상품평 생성 요청 dto
     */
    void addProductReview(MultipartFile file, ProductReviewRequestDto createRequest);

    /**
     * 상품평 수정 서비스 메서드입니다.
     *
     * @param file          상품평 이미지
     * @param modifyRequest 상품평 수정 요청 dto
     */
    void modifyProductReview(MultipartFile file, ProductReviewRequestDto modifyRequest);

    /**
     * 상품평 제거 서비스 메서드입니다.
     *
     * @param orderProductNo 삭제할 상품평 번호
     */
    void removeProductReview(Integer orderProductNo);

    /**
     * 상품평 단건 조회 서비스 메서드입니다.
     *
     * @param orderProductNo 조회할 상품평 번호
     * @return the product review response dto
     */
    ProductReviewResponseDto findReview(Integer orderProductNo);

    /**
     * 상품평 전체 조회 서비스 메서드입니다.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<ProductReviewResponseDto> findReviews(Pageable pageable);

    /**
     * 상품번호로 상품평 다건 조회 서비스 메서드입니다.
     *
     * @param productNo the product no
     * @param pageable  the pageable
     * @return the page
     */
    Page<ProductReviewResponseDto> findReviewsByProductNo(Integer productNo, Pageable pageable);

    /**
     * 회원번호로 상품평 다건 조회 서비스 메서드입니다.
     *
     * @param memberNo the member no
     * @param pageable the pageable
     * @return the page
     */
    Page<ProductReviewResponseDto> findReviewsByMemberNo(Integer memberNo, Pageable pageable);
}
