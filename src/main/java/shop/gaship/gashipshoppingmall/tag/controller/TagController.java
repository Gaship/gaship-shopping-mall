package shop.gaship.gashipshoppingmall.tag.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.service.TagService;

import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.controller
 * fileName       : TagController
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * .
     * methodName : register
     * author : choijungwoo
     * description : Tag PostMapping
     *
     * @param tagRequestDto TagRequestDto
     * @return response entity
     */
    @PostMapping("/admin/{adminNo}/tags")
    public ResponseEntity<TagResponseDto> register(@RequestBody TagRequestDto tagRequestDto) {
        TagResponseDto tagResponseDto = tagService.register(tagRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagResponseDto);
    }

    /**
     * .
     * methodName : modify
     * author : choijungwoo
     * description : Tag PutMapping
     *
     * @param tagRequestDto TagRequestDto
     * @return response entity
     */
    @PutMapping("/admin/{adminNo}/tags/{tagNo}")
    public ResponseEntity<TagResponseDto> modify(@RequestBody TagRequestDto tagRequestDto) {
        TagResponseDto tagResponseDto = tagService.modify(tagRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDto);
    }

    /**
     * .
     * methodName : delete
     * author : choijungwoo
     * description : Tag PostMapping
     *
     * @return response entity
     */
    @DeleteMapping("/admin/{adminNo}/tags/{tagNo}")
    public void delete(@PathVariable Integer tagNo) {
        tagService.delete(tagNo);
    }

    /**
     * .
     * methodName : get
     * author : choijungwoo
     * description : Tag GetMapping
     *
     * @param tagNo Integer
     * @return response entity
     */
    @GetMapping("/admin/{adminNo}/tags/{tagNo}")
    public ResponseEntity<TagResponseDto> get(@PathVariable Integer tagNo) {
        TagResponseDto tagResponseDto = tagService.get(tagNo);
        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDto);
    }

    /**
     * .
     * methodName : getList
     * author : choijungwoo
     * description : Tag GetMapping
     *
     * @return response entity
     */
    @GetMapping("/admin/{adminNo}/tags")
    public ResponseEntity<List<TagResponseDto>> getList(Pageable pageable) {
        List<TagResponseDto> tagResponseDtoList = tagService.getList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDtoList);
    }

}
