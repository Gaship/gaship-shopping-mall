package shop.gaship.gashipshoppingmall.product.service;

import org.springframework.web.multipart.MultipartFile;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.SalesStatusModifyRequestDto;

import java.io.IOException;
import java.util.List;

/**
 * 상품 서비스 인터페이스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
public interface ProductService {
    void addProduct(List<MultipartFile> files, ProductCreateRequestDto createRequest) throws IOException;

    void modifyProduct(List<MultipartFile> multipartFile, ProductModifyRequestDto modifyRequest) throws IOException;

    void modifyProductSalesStatus(SalesStatusModifyRequestDto salesStatusModifyRequest);
}
