package shop.gaship.gashipshoppingmall.totalsale.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipshoppingmall.totalsale.service.TotalSaleService;

/**
 * 매출현황을 조회하기위한 컨트롤러 클래스입니다.
 *
 * @author : 유호철, 조재철
 * @since 1.0
 */

@RestController
@RequestMapping("/api/total-sale")
@RequiredArgsConstructor
public class TotalSaleController {

    private final TotalSaleService service;

    /**
     * 날짜를 입력받아서 매출현황을 조회하는 메서드입니다..
     *
     * @return 조회된 매출현황이 리스트로 반환된다.
     */
    @PostMapping
    public ResponseEntity<List<TotalSaleResponseDto>> findTotalSaleList(
        @RequestBody TotalSaleRequestDto totalSaleRequestDto) {
        List<TotalSaleResponseDto> totalSale =
            service.findTotalSales(totalSaleRequestDto);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(totalSale);
    }
}
