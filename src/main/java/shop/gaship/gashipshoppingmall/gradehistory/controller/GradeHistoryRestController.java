package shop.gaship.gashipshoppingmall.gradehistory.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryFindRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.dto.response.GradeHistoryResponseDto;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

/**
 * 등급이력 Rest Controller.
 *
 * @author : 김세미
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/gradehistories")
public class GradeHistoryRestController {
    private final GradeHistoryService gradeHistoryService;

    /**
     * 등급이력 Post Mapping
     * 등급이력 등록을 위한 Rest Controller 메서드.
     *
     * @param requestDto 등급이력 등록 요청 data transfer object (GradeHistoryAddRequestDto)
     * @return responseEntity body 는 없으며 응답 status 는 Created.
     * @author 김세미
     */
    @PostMapping
    public ResponseEntity<Void>
        gradeHistoryAdd(@Valid @RequestBody GradeHistoryAddRequestDto requestDto) {

        gradeHistoryService.addGradeHistory(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    /**
     * 등급이력 Get Mapping
     * 등급이력 다건 조회를 위한 Rest Controller 메서드.
     *
     * @param requestDto 등급이력 다건 조회 요청 data transfer object (GradeHistoryFindRequestDto)
     * @return responseEntity page 정보와 다건 조회 결과가 body 에 담기며 status 는 OK.
     * @author 김세미
     */
    @GetMapping
    public ResponseEntity<PageResponse<GradeHistoryResponseDto>>
        gradeHistoryList(@Valid @RequestBody GradeHistoryFindRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(gradeHistoryService.findGradeHistories(requestDto));
    }
}
