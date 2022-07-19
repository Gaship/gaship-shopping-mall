package shop.gaship.gashipshoppingmall.membertag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.membertag.dto.MemberTagRequestDto;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;
import shop.gaship.gashipshoppingmall.membertag.service.MemberTagService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class MemberTagController {
    private final MemberTagService memberTagService;

    @PostMapping("/members/{memberNo}/tags")
    public ResponseEntity<Void> MemberTagDeleteAllAndAddAll(@Valid @RequestBody MemberTagRequestDto memberTagRequestDto){
        memberTagService.deleteAllAndAddAllMemberTags(memberTagRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping("/members/{memberNo}/tags")
    public ResponseEntity<List<MemberTag>> MemberTagList(@PathVariable Integer memberNo){
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberTagService.findMemberTags(memberNo));
    }
}
