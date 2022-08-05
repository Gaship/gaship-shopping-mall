package shop.gaship.gashipshoppingmall.totalsale.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.gaship.gashipshoppingmall.orderproduct.repository.OrderProductRepository;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipshoppingmall.totalsale.exception.LocalDateMaxYearException;
import shop.gaship.gashipshoppingmall.totalsale.service.TotalSaleService;

/**
 * 매출현황을 보기위한 서비스 구현체클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class TotalSaleServiceImpl implements TotalSaleService {
    private final OrderProductRepository repository;

    @Override
    public List<TotalSaleResponseDto> findTotalSaleList(TotalSaleRequestDto dto) {
        if (dto.getEndDate().isAfter(dto.getStartDate().plusYears(1).plusMinutes(1))) {
            throw new LocalDateMaxYearException();
        }
        return repository.findTotalSale(dto);
    }
}
