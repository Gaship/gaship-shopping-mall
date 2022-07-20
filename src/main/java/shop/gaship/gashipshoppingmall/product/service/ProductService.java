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
    /**
     * 상품 등록 메서드입니다.
     *
     * @param files 리스트 형태의 다중 이미지 파일
     * @param createRequest 상품 생성 요청 dto
     * @throws IOException 파일 업로드 시 IOException 이 발생할 수 있습니다.
     * @author 김보민
     */
    void addProduct(List<MultipartFile> files, ProductCreateRequestDto createRequest) throws IOException;

    /**
     * 상품 수정 메서드입니다.
     *
     * @param files 리스트 형태의 다중 이미지 파일
     * @param modifyRequest 상품 수정 요청 dto
     * @throws IOException 파일 업로드 시 IOException 이 발생할 수 있습니다.
     * @author 김보민
     */
    void modifyProduct(List<MultipartFile> files, ProductModifyRequestDto modifyRequest) throws IOException;

    /**
     * 상품 판매상태를 수정하는 메서드입니다.
     *
     * @param salesStatusModifyRequest 상품 판매상태 수정 요청 dto
     * @author 김보민
     */
    void modifyProductSalesStatus(SalesStatusModifyRequestDto salesStatusModifyRequest);
}
