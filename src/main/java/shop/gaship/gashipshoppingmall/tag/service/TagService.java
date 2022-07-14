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
     *
     * @param tagRequestDto TagRequestDto
     */
    TagResponseDto register(TagRequestDto tagRequestDto);

    /**
     * .
     * methodName : modify
     * author : choijungwoo
     *
     * @param tagRequestDto TagRequestDto
     */
    TagResponseDto modify(TagRequestDto tagRequestDto);

    /**
     * .
     * methodName : delete
     * author : choijungwoo
     *
     * @param tagNo TagNo
     */
    void delete(Integer tagNo);

    /**
     * .
     * methodName : get
     * author : choijungwoo
     *
     * @param tagNo Integer
     */
    TagResponseDto get(Integer tagNo);

    /**
     * .
     * methodName : getList
     * author : choijungwoo
     *
     * @param pageable Pageable
     */
    List<TagResponseDto> getList(Pageable pageable);

    /**
     * .
     * methodName : dtoToEntity
     * author : choijungwoo
     *
     * @param dto TagRequestDto
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
     */
    default TagResponseDto entityToDto(Tag tag){
        return TagResponseDto.builder()
                .title(tag.getTitle())
                .registerDatetime(tag.getRegisterDatetime())
                .modifiedDatetime(tag.getModifiedDatetime())
                .build();
    }
}
