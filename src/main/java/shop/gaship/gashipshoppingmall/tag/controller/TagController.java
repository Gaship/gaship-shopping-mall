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
 * The type Tag controller.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    /**
     * Register response entity.
     *
     * @param tagRequestDto the tag request dto
     * @return the response entity
     */
    @PostMapping("/admin/{adminNo}/tags")
    public ResponseEntity<TagResponseDto> register(@RequestBody TagRequestDto tagRequestDto) {
        TagResponseDto tagResponseDto = tagService.register(tagRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagResponseDto);
    }

    /**
     * Modify response entity.
     *
     * @param tagRequestDto the tag request dto
     * @return the response entity
     */
    @PutMapping("/admin/{adminNo}/tags/{tagNo}")
    public ResponseEntity<TagResponseDto> modify(@RequestBody TagRequestDto tagRequestDto) {
        TagResponseDto tagResponseDto = tagService.modify(tagRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDto);
    }

    /**
     * Delete.
     *
     * @param tagNo the tag no
     */
    @DeleteMapping("/admin/{adminNo}/tags/{tagNo}")
    public void delete(@PathVariable Integer tagNo) {
        tagService.delete(tagNo);
    }

    /**
     * Get response entity.
     *
     * @param tagNo the tag no
     * @return the response entity
     */
    @GetMapping("/admin/{adminNo}/tags/{tagNo}")
    public ResponseEntity<TagResponseDto> get(@PathVariable Integer tagNo) {
        TagResponseDto tagResponseDto = tagService.get(tagNo);
        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDto);
    }

    /**
     * Gets list.
     *
     * @param pageable the pageable
     * @return the list
     */
    @GetMapping("/admin/{adminNo}/tags")
    public ResponseEntity<List<TagResponseDto>> getList(Pageable pageable) {
        List<TagResponseDto> tagResponseDtoList = tagService.getList(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(tagResponseDtoList);
    }

}
