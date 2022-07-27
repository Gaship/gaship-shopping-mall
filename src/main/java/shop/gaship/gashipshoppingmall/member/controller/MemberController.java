package shop.gaship.gashipshoppingmall.member.controller;

import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.member.dto.response.*;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

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
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입의 양식 데이터 객체입니다.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<Void> memberAdd(@Valid @RequestBody MemberCreationRequest memberCreationRequest) {
        if (memberCreationRequest.getIsUniqueEmail() &&
                memberCreationRequest.getIsVerifiedEmail()) {
            memberService.addMember(memberCreationRequest);
            return ResponseEntity.created(URI.create("/api/members")).build();
        }

        throw new SignUpDenyException("이메일 중복확인 또는 이메일 검증이 필요합니다.");
    }

    /**
     * 소셜계정으로의 회원가입을 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequestOauth 소셜 회원가입의 양식 데이터 객체입니다.
     */
    @PostMapping(value = "/sign-up/oauth")
    public ResponseEntity<Void> memberAdd(@RequestBody MemberCreationRequestOauth memberCreationRequestOauth) {
        memberService.addMemberByOauth(memberCreationRequestOauth);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 이메일이 이미 존재하는지 요청을 받는 메서드입니다.
     *
     * @param email 이메일
     * @return 결과를 담은 객체를 반환합니다.
     */
    @GetMapping(value = "/check-email", params = "email")
    public ResponseEntity<EmailPresence> checkDuplicateEmail(@RequestParam String email) {
        return ResponseEntity.ok(new EmailPresence(memberService.isAvailableEmail(email)));
    }

    /**
     * 닉네임이 이미 존재하는지 요청을 받는 메서드입니다.
     *
     * @param nickname 닉네임
     * @return 같은 닉네임이 존재하는지에 대한 결과입니다.
     */
    @GetMapping(value = "/check-nickname", params = "nickname")
    public ResponseEntity<NicknamePresence> checkDuplicateNickname(@RequestParam String nickname) {
        return ResponseEntity.ok(
            new NicknamePresence(memberService.isAvailableNickname(nickname)));
    }

    /**
     * 닉네임을 통해 이미 회원이 존재하는지에 대한 요청을 받는 매서드입니다.
     *
     * @param nickname 닉네임
     * @return 회원번호가 담긴 객체를 반환합니다.
     */
    @GetMapping(value = "/recommend-member", params = "nickname")
    public ResponseEntity<MemberNumberPresence> retrieveFromNickname(
            @RequestParam String nickname) {
        return ResponseEntity.ok(new MemberNumberPresence(
                memberService.findMemberFromNickname(nickname).getMemberNo()));
    }

    /**
     * 일반 로그인시 이메일을 통해서 로그인하려는 사용자의 정보를 불러옵니다.
     *
     * @param email 사용자의 정보를 조회할 이메일입니다.
     * @return 로그인에 필요한 필수정보를 반환합니다.
     */
    @GetMapping(value = "/user-detail", params = "email")
    public ResponseEntity<SignInUserDetailsDto> retrieveUserDetail(@RequestParam String email) {
        return ResponseEntity.ok(memberService.findSignInUserDetailFromEmail(email));
    }

    /**
     * 등록된 회원중 마지막 번호를 가진 회원의 번호를 검색하는 기능입니다.
     *
     * @return 회원번호를 반환합니다.
     */
    @GetMapping("/members/last-no")
    public ResponseEntity<Integer> retrieveLastNo() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
            .body(memberService.findLastNo());
    }

    /**
     * 멤버의 정보를 수정하는 메서드입니다.
     *
     * @param memberModifyRequestDto 수정하려는 멤버의 정보를 가지고있는 DTO입니다.
     * @return http body data가 없고 응답 상태는 200을 반환합니다.
     */
    @PutMapping("/{memberNo}")
    public ResponseEntity<Void> memberModify(
        @Valid @RequestBody MemberModifyRequestDto memberModifyRequestDto) {
        memberService.modifyMember(memberModifyRequestDto);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 멤버의 정보를 삭제하는 메서드입니다.
     *
     * @param memberNo 멤버의 고유번호입니다.
     * @return http body data가 없고 응답 상태는 200을 반환합니다.
     */
    @DeleteMapping("/{memberNo}")
    public ResponseEntity<Void> memberRemove(@PathVariable Integer memberNo) {
        memberService.removeMember(memberNo);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 멤버의 상세정보를 조회하는 메서드입니다.
     *
     * @param memberNo 멤버 고유번호
     * @return 조회된 맴버의 상세정보를 반환하고 응답 상태는 200을 반환합니다.
     */
    @GetMapping("/{memberNo}")
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
     * @return 멤버의 전체정보가 있는 객체를 반환합니다.
     */
    @GetMapping(value = "/members/email/{email}")
    public ResponseEntity<MemberResponseDto> memberDetails(@PathVariable String email) {
        MemberResponseDto memberResponseDto = memberService.findMemberFromEmail(email);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberResponseDto);
    }

    /**
     * 멤버 다건조회를 위한 메서드입니다.
     *
     * @param pageable page와 size가 쿼리 파라미터로 담긴 객체입니다.
     * @return body는 조회된 멤버들의 정보, 응답 상태는 200을 반환합니다.
     */
    @GetMapping
    public ResponseEntity<MemberPageResponseDto<MemberResponseDto, Member>> memberList(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberService.findMembers(pageable));
    }

    /**
     * 관리자가 멤버의 활성상태정보를 수정하기위한 매서드입니다.
     *
     * @param request 변경하고싶은 회원의 상태, 닉네임 정보가있는 객체입니다.
     * @return body 데이터는 없고, 응답 상태는 200을 반환합니다.
     */
    @PutMapping("/{memberNo}/role")
    public ResponseEntity<Void> memberModifyByAdmin(MemberModifyRequestDto request) {
        memberService.modifyMember(request);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }
}
