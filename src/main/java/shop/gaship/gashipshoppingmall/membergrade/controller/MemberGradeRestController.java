package shop.gaship.gashipshoppingmall.membergrade.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeRequestDto;
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
     * description : MemberGrade PostMapping
     *
     * @param request MemberGradeRequest
     * @return response entity
     */
    @PostMapping
    public ResponseEntity<Void> memberGradeAdd(@RequestBody MemberGradeRequestDto request) {
        memberGradeService.addMemberGrade(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * .
     * methodName : memberGradeModify
     * author : Semi Kim
     * description : MemberGrade PutMapping
     *
     * @param memberGradeNo Integer
     * @param request       MemberGradeRequest
     * @return response entity
     */
    @PutMapping("/{memberGradeNo}")
    public ResponseEntity<Void> memberGradeModify(@PathVariable Integer memberGradeNo,
                                                  @RequestBody MemberGradeRequestDto request) {
        memberGradeService.modifyMemberGrade(memberGradeNo, request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * .
     * methodName : memberGradeRemove
     * author : Semi Kim
     * description : MemberGrade DeleteMapping
     *
     * @param memberGradeNo Integer
     * @return response entity
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
     * description : MemberGrade Detail GetMapping
     *
     * @param memberGradeNo Integer
     * @return response entity
     */
    @GetMapping("/{memberGradeNo}")
    public ResponseEntity<MemberGradeDto> memberGradeDetails(@PathVariable Integer memberGradeNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberGradeService.findMemberGrade(memberGradeNo));
    }

    /**
     * .
     * methodName : memberGradeList
     * author : Semi Kim
     * description : MemberGrade List GetMapping
     *
     * @param page Pageable
     * @return response entity
     */
    @GetMapping
    public ResponseEntity<List<MemberGradeDto>> memberGradeList(Pageable page) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberGradeService.findMemberGrades(page));
    }
}
