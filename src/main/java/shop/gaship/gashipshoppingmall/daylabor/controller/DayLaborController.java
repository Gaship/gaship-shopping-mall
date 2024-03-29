package shop.gaship.gashipshoppingmall.daylabor.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.daylabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.daylabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.daylabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.daylabor.service.DayLaborService;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * 지역별물량에대한 요청을 다루는 컨틀롤러입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/api/dayLabors")
@RequiredArgsConstructor
public class DayLaborController {
    private final DayLaborService service;

    /**
     * post 요청을받아 지역별물량을 생성하기위한 메서드입니다.
     *
     * @param requestDto 지역별물양을생성을 위해 지역번호와 최대물량이 있습니다.
     * @author 유호철
     */
    @PostMapping
    public ResponseEntity<Void> addDayLabor(
        @Valid @RequestBody CreateDayLaborRequestDto requestDto) {
        service.addDayLabor(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
            .build();
    }

    /**
     * put 요청을 받아 최대일량을 수정하기위한 메서드입니다.
     *
     * @param requestDto 지역번호와 최대물량을 가지고있습니다.
     * @author 유호철
     */
    @PutMapping
    public ResponseEntity<Void> modifyDayLabor(
        @Valid @RequestBody FixDayLaborRequestDto requestDto) {
        service.modifyDayLabor(requestDto);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
    }

    /**
     * get 요청을 받아서 전체 물량을 조회하기위한 메서드입니다.
     *
     * @return list : 조회된 전체 지역물량을 보여주는 메서드입니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<PageResponse<GetDayLaborResponseDto>> dayLaborList(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(service.findDayLabors(pageable));
    }
}
