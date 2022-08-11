package shop.gaship.gashipshoppingmall.member.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyByAdminDto;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDtoByAdmin;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import shop.gaship.gashipshoppingmall.response.PageResponse;

/**
 * 관리자가 멤버의 정보를 요청하거나 수정 할 때 쓰는 controller.
 *
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/members")
public class MemberByAdminController {
    private final MemberService memberService;

    /**
     * 관리자가 멤버의 활성상태정보를 수정하기위한 매서드입니다.
     *
     * @param request 변경하고싶은 회원의 상태, 닉네임 정보가있는 객체입니다.
     * @return body 데이터는 없고, 응답 상태는 200을 반환합니다.
     */
    @PutMapping("/{memberNo}")
    public ResponseEntity<Void> memberModifyByAdmin(
            @Valid @RequestBody MemberModifyByAdminDto request) {
        memberService.modifyMemberByAdmin(request);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
    }


    /**
     * 관리자가 멤버의 상세정보를 조회하는 메서드입니다.
     *
     * @param memberNo 멤버 고유번호
     * @return 조회된 멤버의 상세정보를 반환하고 응답 상태는 200을 반환합니다.
     */
    @GetMapping("/{memberNo}")
    public ResponseEntity<MemberResponseDtoByAdmin> memberDetailsByAdmin(
            @PathVariable Integer memberNo) {
        MemberResponseDtoByAdmin dto = memberService.findMemberByAdmin(memberNo);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(dto);
    }

    /**
     * 관리자가 멤버 다건조회를 위한 메서드입니다.
     *
     * @param pageable page와 size가 쿼리 파라미터로 담긴 객체입니다.
     * @return body 는 조회된 멤버들의 정보, 응답 상태는 200을 반환합니다.
     */
    @GetMapping
    public ResponseEntity<PageResponse<MemberResponseDtoByAdmin>> memberList(
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(memberService.findMembers(pageable));
    }

}
