package shop.gaship.gashipshoppingmall.member.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.member.dto.EmailPresence;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberNumberPresence;
import shop.gaship.gashipshoppingmall.member.dto.MemberPageResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.member.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * member 등록, 수정, 삭제, 회원등록과 관련된 요청을 수행하는 restController 입니다.
 *
 * @author 김민수
 * @author 최정우
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입의 양식 데이터 객체입니다.
     */
    @PostMapping("/members")
    public ResponseEntity<Void> memberAdd(
        @Valid @RequestBody MemberCreationRequest memberCreationRequest) {
        if (memberCreationRequest.getIsUniqueEmail() &&
            memberCreationRequest.getIsVerifiedEmail()) {
            memberService.addMember(memberCreationRequest);
            return ResponseEntity.created(URI.create("/members")).body(null);
        }

        throw new SignUpDenyException("이메일 중복확인 또는 이메일 검증이 필요합니다.");
    }

    /**
     * 소셜계정으로의 회원가입을 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequestOauth 소셜 회원가입의 양식 데이터 객체입니다.
     */
    // TODO : member가 맞지않을까요?
    @PostMapping("/members")
    public ResponseEntity<Void> memberAdd(@RequestBody MemberCreationRequestOauth memberCreationRequestOauth) {
        memberService.addMember(memberCreationRequestOauth);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 이메일이 이미 존재하는지 요청을 받는 메서드입니다.
     *
     * @param email 이메일
     * @return 결과를 담은 객체를 반환합니다.
     */
    @GetMapping(value = "/members/retrieve", params = "email")
    public ResponseEntity<EmailPresence> retrieveFromEmail(@RequestParam String email) {
        return ResponseEntity.ok(new EmailPresence(memberService.isAvailableEmail(email)));
    }

    /**
     * 닉네임을 통해 이미 회원이 존재하는지에 대한 요청을 받는 매서드입니다.
     *
     * @param nickname 닉네임
     * @return 회원번호가 담긴 객체를 반환합니다.
     */
    @GetMapping(value = "/members/retrieve", params = "nickname")
    public ResponseEntity<MemberNumberPresence> retrieveFromNickname(
        @RequestParam String nickname) {
        return ResponseEntity.ok(new MemberNumberPresence(
            memberService.findMemberFromNickname(nickname).getMemberNo()));
    }

    /**
     * 등록된 회원중 마지막 번호를 가진 회원의 번호를 검색하는 기능입니다.
     *
     * @return 회원번호를 반환합니다.
     */
    @GetMapping("/members/lastNo")
    public ResponseEntity<Integer> retrieveLastNo() {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(memberService.findLastNo());
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
     * email로 member를 조회하고 memberResponseDto로 변경한뒤 responseEntity를 반환하는 기능입니다.
     *
     * @param email 요청받은 email 정보입니다.
     * @return ResponseEntity<MemberResponseDto> 변경된 dto를 entity화시켜서 반환합니다.
     */
    // TODO : 회원entity에 소셜회원가입여부 추가 true false
    @GetMapping("/members/{email}")
    public ResponseEntity<MemberResponseDto> memberDetails(@PathVariable String email) {
        MemberResponseDto memberResponseDto = memberService.findMemberFromEmail(email);
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
    public ResponseEntity<MemberPageResponseDto<MemberResponseDto, Member>> memberList(Pageable pageable) {
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
