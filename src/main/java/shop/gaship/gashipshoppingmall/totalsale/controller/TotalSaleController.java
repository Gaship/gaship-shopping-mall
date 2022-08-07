package shop.gaship.gashipshoppingmall.totalsale.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.totalsale.dto.request.TotalSaleRequestDto;
import shop.gaship.gashipshoppingmall.totalsale.dto.response.TotalSaleResponseDto;
import shop.gaship.gashipshoppingmall.totalsale.service.TotalSaleService;

/**
 * 매출현황을 조회하기위한 컨트롤러 클래스입니다.
 *
 * @author : 유호철
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
    @GetMapping(value = "/date/{start}/end/{end}")
    public ResponseEntity<List<TotalSaleResponseDto>> findTotalSaleList(
        @PathVariable("start")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime start,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        @PathVariable("end")
        LocalDateTime end) {
        List<TotalSaleResponseDto> totalSale =
            service.findTotalSales(new TotalSaleRequestDto(start, end));
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(totalSale);
    }
}
