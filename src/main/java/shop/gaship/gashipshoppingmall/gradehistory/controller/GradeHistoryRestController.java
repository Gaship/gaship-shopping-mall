package shop.gaship.gashipshoppingmall.gradehistory.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.gradehistory.service.GradeHistoryService;

/**
 * 등급이력 Rest Controller.
 *
 * @author : 김세미
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/gradehistory")
public class GradeHistoryRestController {
    private final GradeHistoryService gradeHistoryService;

    /**
     * 등급이력 Post Mapping
     * 등급이력 등록을 위한 Rest Controller 메서드.
     *
     * @param request 등급이력 등록 요청 data transfer object
     * @return responseEntity body 는 없으며 응답 status 는 Created.
     * @author 김세미
     */
    @PostMapping
    public ResponseEntity<Void>
        gradeHistoryAdd(@Valid @RequestBody GradeHistoryAddRequestDto request) {

        gradeHistoryService.addGradeHistory(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
