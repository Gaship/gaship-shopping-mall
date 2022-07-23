package shop.gaship.gashipshoppingmall.tag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.tag.dto.response.PageResponseDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.request.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.response.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;
import shop.gaship.gashipshoppingmall.tag.service.TagService;

import javax.validation.Valid;

/**
 * * 태그의 restController 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;

    /**
     * 태그 Post Mapping
     * 태그 등록을 위한 RestController 메서드.
     *
     * @param request 태그를 등록하기 위한 정보를 담는 dto 입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 최정우
     */
    @PostMapping
    public ResponseEntity<Void> TagAdd(@Valid @RequestBody TagAddRequestDto request) {
        tagService.addTag(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 태그 Put Mapping
     * 태그 수정을 위한 RestController 메서드.
     *
     * @param request 태그를 수정하기 위한 정보를 담는 dto 입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 OK.
     * @author 최정우
     */
    @PutMapping("/{tagNo}")
    public ResponseEntity<Void> TagModify(@Valid @RequestBody TagModifyRequestDto request) {
        tagService.modifyTag(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 태그 Get Mapping
     * 태그 단건 조회를 위한 RestController 메서드.
     *
     * @param tagNo 단건 조회하려는 태그의 식별번호 (Integer)
     * @return responseEntity body 는 조회된 태그의 정보, 응답 status 는 OK .
     * @author 최정우
     */
    @GetMapping("/{tagNo}")
    public ResponseEntity<TagResponseDto> TagDetails(@PathVariable Integer tagNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tagService.findTag(tagNo));
    }

    /**
     * 태그 Get Mapping
     * 태그 다건 조회를 위한 RestController 메서드.
     *
     * @param pageable page 와 size 가 담겨져있음
     * @return responseEntity body 는 조회된 페이지의 정보, 응답 status 는 OK .
     * @author 최정우
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<TagResponseDto, Tag>> TagList(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tagService.findTags(pageable));
    }
}
