package shop.gaship.gashipshoppingmall.membertag.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.membertag.dto.request.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.dto.response.MemberTagResponseDto;
import shop.gaship.gashipshoppingmall.membertag.service.MemberTagService;

/**
 * 멤버태그 등록삭제, 조회를 하기 위한 컨트롤러입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members/{memberNo}/member-tag")
public class MemberTagController {
    private final MemberTagService memberTagService;

    /**
     * 멤버가 설정한 태그를 삭제하고 다시 설정하길 원하는 태그들을 설정하는 컨트롤러입니다.
     *
     * @param memberTagRequestDto 회원 번호와 회원에게 부여하려하는 태그 id 목록들이 들어가 있습니다.
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<Void> memberTagDeleteAllAndAddAll(
            @Valid @RequestBody MemberTagRequestDto memberTagRequestDto) {
        memberTagService.deleteAllAndAddAllMemberTags(memberTagRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 회원이 설정한 멤버태그들을 설정하기위한 메서드입니다.
     *
     * @param memberNo 회원의 id 값입니다.
     * @return 회원이 그 태그를 선택했는지의 여부가 있는 태그 목록이 보입니다.
     */
    @GetMapping
    public ResponseEntity<List<MemberTagResponseDto>> memberTagList(
            @PathVariable Integer memberNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberTagService.findMemberTags(memberNo));
    }
}
