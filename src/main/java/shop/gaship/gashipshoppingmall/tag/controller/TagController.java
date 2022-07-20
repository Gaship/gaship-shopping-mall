package shop.gaship.gashipshoppingmall.tag.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.tag.dto.TagPageResponseDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.exception.DuplicatedTagTitleException;
import shop.gaship.gashipshoppingmall.tag.service.TagService;

/**
 * tag 등록 수정삭제를 하는 restcontroller 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * Tag add response entity.
     * description : 태그를 등록하는 컨트롤러
     *
     * @param request the request
     * @return the response entity
     * @throws DuplicatedTagTitleException 태그를 등록할 때 등록하려는 태그명이 기존의 태그명중에 있을경우 에러가 발생합니다.
     */
    @PostMapping("/admins/{adminNo}/tags")
    public ResponseEntity<Void> TagAdd(@RequestBody TagRequestDto request) {
        tagService.addTag(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * Tag modify response entity.
     * description : 태그를 수정하는 컨트롤러
     *
     * @param request the request
     * @return the response entity
     * @throws DuplicatedTagTitleException 태그를 등록할 때 등록하려는 태그명이 기존의 태그명중에 있을경우 에러가 발생합니다.
     */
    @PutMapping("/admins/{adminNo}/tags")
    public ResponseEntity<Void> TagModify(@RequestBody TagRequestDto request) {
        tagService.modifyTag(request);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * Tag remove response entity.
     * description : 태그를 삭제하는 컨트롤러
     *
     * @param tagNo the tag no
     * @return the response entity
     */
    @DeleteMapping("/admins/{adminNo}/tags/{tagNo}")
    public ResponseEntity<Void> TagRemove(@PathVariable Integer tagNo) {
        tagService.removeTag(tagNo);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * Tag details response entity.
     * description : 태그를 단건조회하는 컨트롤러
     *
     * @param tagNo the tag no
     * @return the response entity
     * * @throws TagNotFoundException 조회하려는 태그가 없을경우 발생하는 에러입니다.
     */
    @GetMapping("/admins/{adminNo}/tags/{tagNo}")
    public ResponseEntity<TagResponseDto> TagDetails(@PathVariable Integer tagNo) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tagService.findTag(tagNo));
    }

    /**
     * Tag list response entity.
     * description : 태그를 다건조회하는 컨트롤러
     *
     * @param pageable the pageable
     * @return the response entity
     */
    @GetMapping("/admins/{adminNo}/tags")
    public ResponseEntity<TagPageResponseDto> TagList(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(tagService.findTags(pageable));
    }

}
