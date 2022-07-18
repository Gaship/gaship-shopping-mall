package shop.gaship.gashipshoppingmall.tag.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.service
 * fileName       : TagService
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
public interface TagService {
    /**
     * .
     * methodName : register
     * author : choijungwoo
     * description : 태그를 등록하는 메서드
     *
     * @param request the request
     */
    void addTag(TagRequestDto request);

    /**
     * .
     * methodName : modify
     * author : choijungwoo
     * description : 태그를 수정하는 메서드(title만 바뀐다)
     *
     * @param request the request
     */
    void modifyTag(TagRequestDto request);

    /**
     * .
     * methodName : delete
     * author : choijungwoo
     * description : 태그를 삭제하는 메서드
     * @param tagNo TagNo
     */
    void removeTag(Integer tagNo);

    /**
     * .
     * methodName : get
     * author : choijungwoo
     * description : 태그를 조회하는 메서드
     *
     * @param tagNo Integer
     * @return the tag response dto
     */
    TagResponseDto findTag(Integer tagNo);

    /**
     * .
     * methodName : getList
     * author : choijungwoo
     * description : 태그를 페이징하는 메서드
     * @param pageable Pageable
     * @return the list
     */
    List<TagResponseDto> findTags(Pageable pageable);

    /**
     * .
     * methodName : dtoToEntity
     * author : choijungwoo
     *
     * @param dto TagRequestDto
     * @return the tag
     */
    default Tag dtoToEntity(TagRequestDto dto){
        return Tag.builder()
                .title(dto.getTitle())
                .build();
    }

    /**
     * .
     * methodName : entityToDto
     * author : choijungwoo
     *
     * @param tag Tag
     * @return the tag response dto
     */
    default TagResponseDto entityToDto(Tag tag){
        return TagResponseDto.builder()
                .title(tag.getTitle())
                .registerDatetime(tag.getRegisterDatetime())
                .modifiedDatetime(tag.getModifiedDatetime())
                .build();
    }
}
