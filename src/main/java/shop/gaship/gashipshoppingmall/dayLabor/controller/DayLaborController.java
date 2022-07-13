package shop.gaship.gashipshoppingmall.dayLabor.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.CreateDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.request.FixDayLaborRequestDto;
import shop.gaship.gashipshoppingmall.dayLabor.dto.response.GetDayLaborResponseDto;
import shop.gaship.gashipshoppingmall.dayLabor.service.DayLaborService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.controller
 * fileName       : DayLaborController
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초생성
 */
@RestController
@RequestMapping("/dayLabors")
@RequiredArgsConstructor
public class DayLaborController {

    private final DayLaborService service;

    /**
     * methodName : registerDayLabor author : 유호철 description : DayLabor 를 생성을 위한 post 메소
     *
     * @param requestDto CreateDayLaborRequestDto
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void registerDayLabor(@Valid @RequestBody CreateDayLaborRequestDto requestDto) {
        service.createDayLabor(requestDto);
    }

    /**
     * methodName : modifyDayLabor author : 유호철 description :
     *
     * @param requestDto FixDayLaborRequestDto
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    void modifyDayLabor(@Valid @RequestBody FixDayLaborRequestDto requestDto) {
        service.modifyDayLabor(requestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<GetDayLaborResponseDto> getAllDayLabor() {
        return service.getAllDayLabors();
    }
}
