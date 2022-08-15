package shop.gaship.gashipshoppingmall.product.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestViewDto;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductAllInfoResponseDto;

/**
 * QueryDsl 을 하기위한 인터페이스 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@NoRepositoryBean
public interface ProductRepositoryCustom {

    /**
     * 조건을 통해 상품들을 조회하기위한 메서드입니다.
     *
     * @param requestDto 조회하기위한 요청들이 담겨여있습니다.
     * @return list 조회된 상품들의 정보가들어있습니다.
     * @author 유호철
     */
    Page<ProductAllInfoResponseDto> findProduct(ProductRequestViewDto requestDto);

}
