package shop.gaship.gashipshoppingmall.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.member.dto.EmailPresence;
import shop.gaship.gashipshoppingmall.member.dto.MemberCreationRequest;
import shop.gaship.gashipshoppingmall.member.dto.MemberNumberPresence;
import shop.gaship.gashipshoppingmall.member.exception.SignUpDenyException;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.controller <br/>
 * fileName       : MemberController <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/12 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/12           김민수               최초 생성                         <br/>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signUp")
    public void signUpMember(@Validated @RequestBody MemberCreationRequest memberCreationRequest) {
        if (memberCreationRequest.getIsUniqueEmail() &&
            memberCreationRequest.getIsVerifiedEmail()) {
            memberService.registerMember(memberCreationRequest);
            return;
        }

        throw new SignUpDenyException("이메일 중복확인 또는 이메일 검증이 필요합니다.");
    }

    @GetMapping(value = "/retrieve", params = "email")
    public EmailPresence retrieveFromEmail(@RequestParam String email) {
        return new EmailPresence(memberService.isAvailableEmail(email));
    }

    @GetMapping(value = "/retrieve", params = "nickname")
    public MemberNumberPresence retrieveFromNickname(@RequestParam String nickname) {
        return new MemberNumberPresence(
            memberService.findMemberFromNickname(nickname).getMemberNo());
    }
}
