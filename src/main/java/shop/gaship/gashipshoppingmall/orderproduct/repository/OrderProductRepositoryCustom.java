package shop.gaship.gashipshoppingmall.orderproduct.repository;

import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleDto;

/**
 * QueryDsl 을 쓰기위한 인터페이스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@NoRepositoryBean
public interface OrderProductRepositoryCustom {

    List<TotalSaleDto> findTotalSale(TotalSaleRequestDto dto);
}
