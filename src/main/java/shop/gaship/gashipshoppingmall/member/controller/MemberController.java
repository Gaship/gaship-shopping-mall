package shop.gaship.gashipshoppingmall.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.member.dto.MemberAddRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

import javax.validation.Valid;
import java.util.List;

/**
 * member 등록, 수정, 삭제를 하는 restController 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    /**
     * Member add response entity.
     *
     * @param request the request
     * @return the response entity
     */
//todo:  valid
    @PostMapping("/signUp")
    public ResponseEntity<Void> memberAdd(@Valid @RequestBody MemberAddRequestDto request) {
        memberService.addMember(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * Member modify response entity.
     *
     * @param memberModifyRequestDto the member modify request dto
     * @return the response entity
     */
    @PutMapping("/members/{memberNo}")
    public ResponseEntity<Void> memberModify(@Valid @RequestBody MemberModifyRequestDto memberModifyRequestDto) {
        memberService.modifyMember(memberModifyRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * Member remove response entity.
     *
     * @param memberNo the member no
     * @return the response entity
     */
    @DeleteMapping("/members/{memberNo}")
    public ResponseEntity<Void> memberRemove(@PathVariable Integer memberNo) {
        memberService.removeMember(memberNo);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * Member details response entity.
     *
     * @param memberNo the member no
     * @return the response entity
     */
    @GetMapping("/members/{memberNo}")
    public ResponseEntity<MemberResponseDto> memberDetails(@PathVariable Integer memberNo) {
        MemberResponseDto memberResponseDto = memberService.findMember(memberNo);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberResponseDto);
    }

    /**
     * Member list response entity.
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping("/members")
    public ResponseEntity<MemberPageResponseDto> memberList(Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberService.findMembers(pageable));
    }

    @PutMapping("/admins/{adminNo}/members")
    public ResponseEntity<Void> memberModifyByAdmin(MemberModifyRequestDto request) {
        memberService.modifyMember(request);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

}
