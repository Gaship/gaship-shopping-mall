package shop.gaship.gashipshoppingmall.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.member.dto.MemberModifyRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberRegisterRequestDto;
import shop.gaship.gashipshoppingmall.member.dto.MemberResponseDto;
import shop.gaship.gashipshoppingmall.member.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<Void> register(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {
        memberService.register(memberRegisterRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/members/{memberNo}")
    public ResponseEntity<Void> modify(@RequestBody MemberModifyRequestDto memberModifyRequestDto) {
        memberService.modify(memberModifyRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/members/{memberNo}")
    public ResponseEntity<Void> delete(@PathVariable Integer memberNo) {
        memberService.delete(memberNo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/members/{memberNo}")
    public ResponseEntity<MemberResponseDto> get(@PathVariable Integer memberNo) {
        MemberResponseDto memberResponseDto = memberService.get(memberNo);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDto>> getList(Pageable pageable) {
        List<MemberResponseDto> list = memberService.getList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }


}
