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
    TagResponseDto register(TagRequestDto tagRequestDto);

    TagResponseDto modify(TagRequestDto tagRequestDto);

    void delete(Integer tagNo);

    TagResponseDto get(Integer tagNo);

    List<TagResponseDto> getList(Pageable pageable);

    default Tag dtoToEntity(TagRequestDto dto){
        return Tag.builder()
                .title(dto.getTitle())
                .build();
    }

    default TagResponseDto entityToDto(Tag tag){
        return TagResponseDto.builder()
                .title(tag.getTitle())
                .registerDatetime(tag.getRegisterDatetime())
                .modifiedDatetime(tag.getModifiedDatetime())
                .build();
    }
}
