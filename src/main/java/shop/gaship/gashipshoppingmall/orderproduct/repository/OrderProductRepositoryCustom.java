package shop.gaship.gashipshoppingmall.orderproduct.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.delivery.dto.DeliveryDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductDetailResponseDto;
import shop.gaship.gashipshoppingmall.orderproduct.dto.response.OrderProductResponseDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;

/**
 * QueryDsl 을 쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@NoRepositoryBean
public interface OrderProductRepositoryCustom {

    List<TotalSaleResponseDto> findTotalSale(TotalSaleRequestDto dto);

    Optional<OrderProductDetailResponseDto> findOrderProductDetail(Integer orderProductNo);

    Page<OrderProductResponseDto> findAllOrdersByMemberNo(Integer memberNo, Pageable pageable);

    Optional<DeliveryDto> findOrderInfo(Integer orderProductNo);
}
