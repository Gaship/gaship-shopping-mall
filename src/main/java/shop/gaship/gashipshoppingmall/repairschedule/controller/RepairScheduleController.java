package shop.gaship.gashipshoppingmall.repairschedule.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.CreateScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.ModifyScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.request.RepairScheduleRequestDto;
import shop.gaship.gashipshoppingmall.repairschedule.dto.response.GetRepairScheduleResponseDto;
import shop.gaship.gashipshoppingmall.repairschedule.service.RepairScheduleService;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * 수리설치일자에대한 요청을 다루기위한 컨트롤러입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@RestController
@RequestMapping("/api/repair-schedule")
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
     * 스케줄에대한 정보를 페이징으로 보여주기위한 메서드입니다.
     *
     * @param pageable 요청 페이지 정보가 기입됩니다.
     * @return response entity 페이지 정보가담긴 객체를 반환한다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<Page<GetRepairScheduleResponseDto>> scheduleListPage(
        Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.findRepairSchedules(pageable));
    }

    /**
     * 일짜별로 조회하기위한 메서드입니다.
     *
     * @param dto 조회하기위한 시작날짜와 만료날짜가 기입됩니다.
     * @return response entity 날짜별로 조회된 스케줄정보를 반환합니다.
     * @author 유호철
     */
    @GetMapping(value = "/date")
    public ResponseEntity<PageResponse<GetRepairScheduleResponseDto>> scheduleList(
        RepairScheduleRequestDto dto,
        Pageable pageable) {
        Page<GetRepairScheduleResponseDto> page = service.findSchedulesByDate(dto, pageable);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new PageResponse<>(page));
    }
}
