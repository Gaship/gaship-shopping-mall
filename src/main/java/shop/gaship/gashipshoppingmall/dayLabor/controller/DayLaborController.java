package shop.gaship.gashipshoppingmall.dayLabor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.dayLabor.service.DayLaborService;

import javax.validation.Valid;
import java.util.List;

/**
 * 지역별물량에대한 요청을 다루는 컨틀롤러입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/dayLabors")
@RequiredArgsConstructor
public class DayLaborController {
    private final DayLaborService service;

    /**
     * put 요청을 받아 최대일량을 수정하기위한 메서드입니다.
     *
     * @param requestDto 지역번호와 최대물량을 가지고있습니다.
     * @author 유호철
     */
    @PutMapping
    public ResponseEntity<Void> dayLaborModify(@Valid @RequestBody FixDayLaborRequestDto requestDto) {
        service.modifyDayLabor(requestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }


    /**
     * post 요청을받아 지역별물량을 생성하기위한 메서드입니다.
     *
     * @param requestDto 지역별물양을생성을 위해 지역번호와 최대물량이 있습니다.
     * @author 유호철
     */
    @PostMapping
    public ResponseEntity<Void> dayLaborAdd(@Valid @RequestBody CreateDayLaborRequestDto requestDto) {
        service.addDayLabor(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * get 요청을 받아서 전체 물량을 조회하기위한 메서드입니다.
     *
     * @return list : 조회된 전체 지역물량을 보여주는 메서드입니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<List<GetDayLaborResponseDto>> dayLaborList() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findDayLabors());
    }
}
