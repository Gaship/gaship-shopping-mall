package shop.gaship.gashipshoppingmall.product.service;

import org.springframework.data.domain.Page;
import shop.gaship.gashipshoppingmall.product.dto.response.ProductResponseDto;

import java.util.List;

/**
 * 상품 서비스 인터페이스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductService {

    /**
     * 제품의 정보를 코드값으로 조회하기위한 메서드입니다.
     *
     * @param productCode 조회할 코드정보입니다.
     * @return list 제품의 정보들이 list 형태로 반환됩니다.
     * @author 유호철
     */
    List<ProductResponseDto> findProductByCode(String productCode);

    /**
     * 제품들을 페이징처리해서 조회하기위한 메서드입니다.
     *
     * @param page 페이지 정보입니다.
     * @param size 페이지 사이즈정보입니다.
     * @author 유호철
     */
    Page<ProductResponseDto> findProducts(int page, int size);

}
