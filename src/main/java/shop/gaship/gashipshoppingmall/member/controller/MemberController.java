package shop.gaship.gashipshoppingmall.member.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.member.dto.EmailPresence;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberNumberPresence;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

/**
 * 회원과 관련된 예외가 발생될 시 해당 클래스에서 예외를 처리합니다.
 *
 * @author 김민수
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 요청을 받는 메서드입니다.
     *
     * @param memberCreationRequest 회원가입의 양식 데이터 객체입니다.
     */
    @PostMapping
    public ResponseEntity<Void> signUpMember(
        @Validated @RequestBody MemberCreationRequest memberCreationRequest) {
        if (memberCreationRequest.getIsUniqueEmail() &&
            memberCreationRequest.getIsVerifiedEmail()) {
            memberService.registerMember(memberCreationRequest);
            return ResponseEntity.created(URI.create("/members")).body(null);
        }

        throw new SignUpDenyException("이메일 중복확인 또는 이메일 검증이 필요합니다.");
    }

    /**
     * 이메일이 이미 존재하는지 요청을 받는 메서드입니다.
     *
     * @param email 이메일
     * @return 결과를 담은 객체를 반환합니다.
     */
    @GetMapping(value = "/retrieve", params = "email")
    public ResponseEntity<EmailPresence> retrieveFromEmail(@RequestParam String email) {
        return ResponseEntity.ok(new EmailPresence(memberService.isAvailableEmail(email)));
    }

    /**
     * 닉네임을 통해 이미 회원이 존재하는지에 대한 요청을 받는 매서드입니다.
     *
     * @param nickname 닉네임
     * @return 회원번호가 담긴 객체를 반환합니다.
     */
    @GetMapping(value = "/retrieve", params = "nickname")
    public ResponseEntity<MemberNumberPresence> retrieveFromNickname(
        @RequestParam String nickname) {
        return ResponseEntity.ok(new MemberNumberPresence(
            memberService.findMemberFromNickname(nickname).getMemberNo()));
    }
}
