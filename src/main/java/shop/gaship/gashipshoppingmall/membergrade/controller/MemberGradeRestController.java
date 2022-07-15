package shop.gaship.gashipshoppingmall.membergrade.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;



/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.controller
 * fileName       : MemberGradeRestController
 * author         : Semi Kim
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        Semi Kim       최초 생성
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/grades")
public class MemberGradeRestController {
    private final MemberGradeService memberGradeService;

    /**
     * .
     * methodName : memberGradeAdd
     * author : Semi Kim
     * description : 회원등급 등록을 위한 RestController 메서드
     *
     * @param request MemberGradeRequest
     * @return responseEntity
     */
    @PostMapping
    public ResponseEntity<Void>
        memberGradeAdd(@Valid @RequestBody MemberGradeAddRequestDto request) {
        memberGradeService.addMemberGrade(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * .
     * methodName : memberGradeModify
     * author : Semi Kim
     * description : 회원등급 수정을 위한 RestController 메서드
     *
     * @param request       MemberGradeModifyRequestDto
     * @return responseEntity
     */
    @PutMapping
    public ResponseEntity<Void>
        memberGradeModify(@Valid @RequestBody MemberGradeModifyRequestDto request) {
        memberGradeService.modifyMemberGrade(request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * .
     * methodName : memberGradeRemove
     * author : Semi Kim
     * description : 회원등급 삭제을 위한 RestController 메서드
     *
     * @param memberGradeNo 삭제하려는 회원등급 식별 번호 (Integer)
     * @return responseEntity
     */
    @DeleteMapping("/{memberGradeNo}")
    public ResponseEntity<Void> memberGradeRemove(@PathVariable Integer memberGradeNo) {
        memberGradeService.removeMemberGrade(memberGradeNo);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * .
     * methodName : memberGradeDetails
     * author : Semi Kim
     * description : 회원등급 단건조회를 위한 RestController 메서드
     *
     * @param memberGradeNo 단건조회하려는 회원등급 식별 번호 (Integer)
     * @return responseEntity
     */
    @GetMapping("/{memberGradeNo}")
    public ResponseEntity<MemberGradeResponseDto>
        memberGradeDetails(@PathVariable Integer memberGradeNo) {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberGradeService.findMemberGrade(memberGradeNo));
    }

    /**
     * .
     * methodName : memberGradeList
     * author : Semi Kim
     * description : pagination 이 적용된 회원등급 다건 조회를 위한 RestController 메서드
     *
     * @param pageable Pageable
     * @return responseEntity
     */
    @GetMapping
    public ResponseEntity<List<MemberGradeResponseDto>> memberGradeList(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberGradeService.findMemberGrades(pageable));
    }
}
