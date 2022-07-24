package shop.gaship.gashipshoppingmall.membertag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.service.MemberTagService;

import javax.validation.Valid;

/**
 * MemberTag 의 restController 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/{memberNo}/tags")
public class MemberTagController {
    private final MemberTagService memberTagService;

    /**
     * 멤버태그 Post Mapping
     * 멤버태그 삭제, 등록을 위한 RestController 메서드.
     *
     * @param memberTagRequestDto 회원의 식별번호와 등록하고자하는 태그들의 식별번호가 담겨있습니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 최정우
     */
    @PostMapping
    public ResponseEntity<Void> MemberTagDeleteAllAndAddAll(@Valid @RequestBody MemberTagRequestDto memberTagRequestDto) {
        memberTagService.deleteAllAndAddAllMemberTags(memberTagRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 멤버태그 Get Mapping
     * 해당 멤버의 멤버태그를 조회하기 위한 RestController 메서드.
     *
     * @param memberNo 회원의 식별번호입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 최정우
     */
    @GetMapping
    public ResponseEntity<MemberTagResponseDto> MemberTagList(@PathVariable Integer memberNo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberTagService.findMemberTags(memberNo));
    }
}
