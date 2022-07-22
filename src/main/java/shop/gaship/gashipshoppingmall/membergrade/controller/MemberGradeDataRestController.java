package shop.gaship.gashipshoppingmall.membergrade.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;

/**
 * server side 회원등급 restController.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestController
@RequestMapping("/grade-data")
@RequiredArgsConstructor
public class MemberGradeDataRestController {
    private final MemberGradeService memberGradeService;

    /**
     * 회원등급 전체 다건 조회 Get Mapping.
     *
     * @return responseEntity 전체 회원등급 목록 (List - MemberGradeResponseDto)
     * @author 김세미
     */
    @GetMapping
    public ResponseEntity<List<MemberGradeResponseDto>> memberGradeDataList() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberGradeService.findMemberGrades());
    }
}
