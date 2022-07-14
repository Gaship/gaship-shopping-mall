package shop.gaship.gashipshoppingmall.repairSchedule.controller;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.SchedulePageRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairSchedule.service.RepairScheduleService;

/**
 *packageName    : shop.gaship.gashipshoppingmall.repairSchedule.controller
 * fileName       : RepairScheduleController
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */

@RestController
@RequestMapping("/repair-schedule")
@RequiredArgsConstructor
public class RepairScheduleController {
    private final RepairScheduleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void addRepairSchedule(@Valid @RequestBody CreateScheduleRequestDto dto) {
        service.registerSchedule(dto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    void fixRepairSchedule(@Valid @RequestBody ModifyScheduleRequestDto dto) {
        service.modifySchedule(dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Page<GetRepairScheduleResponseDto> getAllSchedule(@Valid @RequestBody SchedulePageRequestDto request){
        return service.getAllSchedule(request);
    }

    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    List<GetRepairScheduleResponseDto> getScheduleByDate(LocalDate date){
        return service.findScheduleByDate(date);
    }
}
