package shop.gaship.gashipshoppingmall.member.controller;

import java.net.URI;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.aspact.annotation.MemberOnlyAuthority;
import shop.gaship.gashipshoppingmall.aspact.annotation.MemberValid;
import shop.gaship.gashipshoppingmall.member.dto.request.FindMemberEmailRequest;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberCreationRequestOauth;
import shop.gaship.gashipshoppingmall.member.dto.request.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.request.ReissuePasswordRequest;
import shop.gaship.gashipshoppingmall.member.dto.response.EmailPresence;
import shop.gaship.gashipshoppingmall.member.dto.response.FindMemberEmailResponse;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberNumberPresence;
import shop.gaship.gashipshoppingmall.member.dto.response.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.dto.response.NicknamePresence;
import shop.gaship.gashipshoppingmall.member.dto.response.SignInUserDetailsDto;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

/**
 * member 등록, 수정, 삭제, 회원등록과 관련된 요청을 수행하는 restController 입니다.
 *
 * @author 김민수
 * @author 최정우
 * @author 최겸준
 * @author 조재철
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
    public ResponseEntity<Void> memberAdd(
            @Valid @RequestBody MemberCreationRequest memberCreationRequest) {
            memberService.addMember(memberCreationRequest);

            return ResponseEntity.created(URI.create("/api/members")).build();
    }

    /**
     * 소셜계정으로의 회원가입을 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequestOauth 소셜 회원가입의 양식 데이터 객체입니다.
     */
    @PostMapping(value = "/sign-up/oauth")
    public ResponseEntity<Void> memberAdd(
            @RequestBody MemberCreationRequestOauth memberCreationRequestOauth) {
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
        return ResponseEntity.ok(new NicknamePresence(memberService.isAvailableNickname(nickname)));
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
        return ResponseEntity.ok(
                new MemberNumberPresence(
                        memberService
                                .findMemberFromNickname(nickname)
                                .getMemberNo()));
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
    @GetMapping("/last-no")
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
    @MemberOnlyAuthority
    @PutMapping("/{memberNo}")
    @MemberValid
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
    @MemberOnlyAuthority
    @DeleteMapping("/{memberNo}")
    @MemberValid
    public ResponseEntity<Void> memberRemove(@PathVariable Integer memberNo) {
        memberService.removeMember(memberNo);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
    }


    /**
     * 멤버의 상세정보를 조회하는 메서드입니다.
     *
     * @param memberNo 멤버 고유번호
     * @return 조회된 맴버의 상세정보를 반환하고 응답 상태는 200을 반환합니다.
     */
    @MemberOnlyAuthority
    @GetMapping("/{memberNo}")
    @MemberValid
    public ResponseEntity<MemberResponseDto> memberDetails(@PathVariable Integer memberNo) {
        MemberResponseDto memberResponseDto = memberService.findMember(memberNo);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(memberResponseDto);
    }

    /**
     * email로 member를 조회하고 memberResponseDto로 변경한뒤 responseEntity를 반환하는 기능입니다.
     *
     * @param email 요청받은 email 정보입니다.
     * @return 멤버의 전체정보가 있는 객체를 반환합니다.
     */
    @GetMapping(value = "/email/{email}")
    public ResponseEntity<MemberResponseDto> memberDetails(@PathVariable String email) {
        MemberResponseDto memberResponseDto = memberService.findMemberFromEmail(email);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(memberResponseDto);
    }

    /**
     * 멤버의 이메일을 찾는 요청을 받는 메서드입니다.
     *
     * @param findMemberEmailRequest 멤버의 이메일을 찾기위한 닉네임을 가진 요청객체입니다.
     * @return 이메일의 일부가 가려진 데이터를 가진 객체를 응답(반환)합니다.
     */
    @PostMapping("/find-email")
    public ResponseEntity<FindMemberEmailResponse> memberEmailFromNicknameFind(
            @Valid @RequestBody FindMemberEmailRequest findMemberEmailRequest) {

        return ResponseEntity.ok(
                memberService.findMemberEmailFromNickname(findMemberEmailRequest.getNickname()));
    }

    /**
     * 멤버의 비밀번호 재발급 자격의 여부를 체크하는 메서드입니다.
     *
     * @param reissuePasswordRequest 멤버의 개인정보의 일부입니다.
     * @return 비밀번호 재발급 자격 결과가 담긴 객체입니다.
     */
    @PostMapping("/find-password")
    public ResponseEntity<Map<String, Boolean>> reissuePasswordCheck(
            @Valid @RequestBody ReissuePasswordRequest reissuePasswordRequest) {
        Boolean result = memberService.reissuePassword(reissuePasswordRequest);
        return ResponseEntity.ok(Map.of("changed", result));
    }
}
