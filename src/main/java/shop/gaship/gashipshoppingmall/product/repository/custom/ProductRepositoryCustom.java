package shop.gaship.gashipshoppingmall.product.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;

import java.util.List;
import java.util.Optional;

/**
 * QueryDsl 을 하기위한 인터페이스 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
public interface ProductRepositoryCustom {

    /**
     * 제품번호를 통해 제품을 조회하기 위한 메서드입니다.
     *
     * @param productNo 조회하기위한 제품번호입니다.
     * @return optional 제품값을 optional 로 반환합니다.
     * @author 유호철
     */
    Optional<ProductResponseDto> findByProductNo(Integer productNo);


    /**
     * 조건을 통해 상품들을 조회하기위한 메서드입니다.
     *
     * @param requestDto 조회하기위한 요청들이 담겨여있습니다.
     * @return list 조회된 상품들의 정보가들어있습니다.
     * @author 유호철
     */
    Page<ProductAllInfoResponseDto> findProduct(ProductRequestDto requestDto);
}
