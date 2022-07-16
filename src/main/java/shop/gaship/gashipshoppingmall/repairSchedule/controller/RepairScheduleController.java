package shop.gaship.gashipshoppingmall.repairSchedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.request.SchedulePageRequestDto;
import shop.gaship.gashipshoppingmall.repairSchedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairSchedule.service.RepairScheduleService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * 수리설치일자에대한 요청을 다루기위한 컨트롤러입니다.
 *
 *
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/repair-schedule")
@RequiredArgsConstructor
public class RepairScheduleController {
    private final RepairScheduleService service;

    /**
     * post 요청으로 수리설치스케줄을 생성하기위한 메서드입니다.
     *
     * @param dto 스케줄을 생성하기위한 기본정보들이 담겨있습니다.
     * @author 유호철
     */
    @PostMapping
    public ResponseEntity<Void> addRepairSchedule(
            @Valid @RequestBody CreateScheduleRequestDto dto) {
        service.addRepairSchedule(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 스케줄에대한 정보를 수정하기위한 메서드입니다.
     *
     * @param dto 스케줄정보를 수정하기위한 정보가담겨있습니다.
     * @author 유호철
     */
    @PutMapping
    public ResponseEntity<Void> modifyRepairSchedule(
            @Valid @RequestBody ModifyScheduleRequestDto dto) {
        service.modifyRepairSchedule(dto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * get 요청으로 모든 스케줄정보를 들고오기위한 메서드입니다.
     *
     * @param request 페이지요청정보입니다.
     * @return page 페이지 리스트형식으로 스케줄들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<Page<GetRepairScheduleResponseDto>> scheduleListPage(
            @Valid @RequestBody SchedulePageRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findRepairSchedules(request));
    }

    /**
     * get/date 로 일자로 스케줄을 받기위한 메서드입니다.
     *
     * @param date 일자정보입니다.
     * @return list 일자정보에대한 스케줄들이 반환됩니다.
     * @author 유호철
     */
    @GetMapping("/date")
    public ResponseEntity<List<GetRepairScheduleResponseDto>> scheduleList(LocalDate date) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findSchedulesByDate(date));
    }
}
