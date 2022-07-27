package shop.gaship.gashipshoppingmall.membergrade.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.RenewalMemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.AdvancementTargetResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.service.AdvancementService;


/**
 * 회원승급 관련 rest controller.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member-grades/advancement")
public class AdvancementRestController {
    private final AdvancementService advancementService;

    /**
     * 승급 대상 회원 다건 조회 POST mapping
     * 특정 날짜에 등급 갱신 대상이 되는 회원 목록 조회를 위한 RestController 메서드.
     *
     * @param renewalGradeDate 승급 대상 조회 기준 날짜 (String)
     * @return response entity
     * @author 김세미
     */
    @GetMapping(value = "/target", params = "renewalGradeDate")
    public ResponseEntity<List<AdvancementTargetResponseDto>>
        advancementTargetList(@RequestParam String renewalGradeDate) {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(advancementService.findTargetsByRenewalGradeDate(renewalGradeDate));
    }

    /**
     * 회원승급 POST Mapping
     * 회원의 등급 수정 및 등급이력 등록을 위한 RestController 메서드.
     *
     * @param requestDto 등급이 갱신되는 회원의 정보 및 등급이력 등록 정보 (RenewalMemberGradeRequestDto)
     * @return responseEntity
     * @author 김세미
     */
    @PostMapping
    public ResponseEntity<Void>
        advancementMemberAdd(@Valid @RequestBody RenewalMemberGradeRequestDto requestDto) {

        advancementService.renewalMemberGrade(requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
