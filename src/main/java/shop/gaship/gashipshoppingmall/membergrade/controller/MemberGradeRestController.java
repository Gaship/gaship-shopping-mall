package shop.gaship.gashipshoppingmall.membergrade.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeAddRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.CouponTargetMemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.dto.response.MemberGradeResponseDto;
import shop.gaship.gashipshoppingmall.membergrade.service.MemberGradeService;
import shop.gaship.gashipshoppingmall.util.PageResponse;


/**
 * 회원등급 Rest 컨트롤러.
 *
 * @author : 김세미
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member-grades")
public class MemberGradeRestController {

    private final MemberGradeService memberGradeService;

    /**
     * 회원등급 POST Mapping 회원등급 등록을 위한 RestController 메서드.
     *
     * @param requestDto 등록할 회원등급 정보 (MemberGradeAddRequestDto)
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 김세미
     */
//    @AdminAuthority
    @PostMapping
    public ResponseEntity<Void>
        memberGradeAdd(@Valid @RequestBody MemberGradeAddRequestDto requestDto) {
        memberGradeService.addMemberGrade(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }


    /**
     * 회원등급 PUT Mapping 회원등급 수정을 위한 RestController 메서드.
     *
     * @param memberGradeNo 수정할 회원등급 식별 번호 (Integer)
     * @param requestDto    수정할 회원등급 정보 (MemberGradeModifyRequestDto)
     * @return response entity
     * @author 김세미
     */
//    @AdminAuthority
    @PutMapping("/{memberGradeNo}")
    public ResponseEntity<Void> memberGradeModify(@PathVariable Integer memberGradeNo,
        @Valid @RequestBody MemberGradeModifyRequestDto requestDto) {
        memberGradeService.modifyMemberGrade(memberGradeNo, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    /**
     * 회원등급 DELETE Mapping 회원등급 삭제을 위한 RestController 메서드.
     *
     * @param memberGradeNo 삭제하려는 회원등급 식별 번호 (Integer)
     * @return responseEntity
     * @author 김세미
     */
//    @AdminAuthority
    @DeleteMapping("/{memberGradeNo}")
    public ResponseEntity<Void> memberGradeRemove(@PathVariable Integer memberGradeNo) {
        memberGradeService.removeMemberGrade(memberGradeNo);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    /**
     * 회원등급 GET Mapping 회원등급 단건조회를 위한 RestController 메서드.
     *
     * @param memberGradeNo 단건조회하려는 회원등급 식별 번호 (Integer)
     * @return responseEntity
     * @author 김세미
     */
//    @MemberAuthority
    @GetMapping("/{memberGradeNo}")
    public ResponseEntity<MemberGradeResponseDto>
        memberGradeDetails(@PathVariable Integer memberGradeNo) {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberGradeService.findMemberGrade(memberGradeNo));
    }

    /**
     * 회원등급 GET Mapping pagination 이 적용된 회원등급 다건 조회를 위한 RestController 메서드.
     *
     * @param pageable page 와 size
     * @return responseEntity
     * @author 김세미
     */
//    @AdminAuthority
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<PageResponse<MemberGradeResponseDto>>
        memberGradeList(Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberGradeService.findMemberGrades(pageable));
    }

    /**
     * 회원등급 전체 다건 조회 GET Mapping.
     *
     * @return responseEntity 전체 회원등급 목록 (List - MemberGradeResponseDto)
     * @author 김세미
     */
    @GetMapping
    public ResponseEntity<List<MemberGradeResponseDto>> memberGradeDataList() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(memberGradeService.findMemberGrades());
    }

    /**
     * 존재하는 회원 등급 모두 조회 요청을 받는 메서드.
     *
     * @return 전체 회원등급 리스트.
     */
    @GetMapping("/coupon-target")
    public ResponseEntity<List<CouponTargetMemberGradeResponseDto>> findCouponTargetGrade() {

        return ResponseEntity.status(HttpStatus.OK)
            .body(memberGradeService.findCouponTargetGrade());
    }
}
