package shop.gaship.gashipshoppingmall.tag.service;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.tag.dto.PageResponse;
import shop.gaship.gashipshoppingmall.tag.dto.TagAddRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagModifyRequestDto;
import shop.gaship.gashipshoppingmall.tag.dto.TagResponseDto;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * 태그의 service interface 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
public interface TagService {
    /**
     * 태그 등록을 하기 위한 메서드
     *
     * @param request 등록에 필요한 정보를 담고있는 dto 입니다.
     */
    void addTag(TagAddRequestDto request);

    /**
     * 태그 수정을 하기 위한 메서드
     *
     * @param request 수정에 필요한 정보를 담고있는 dto 입니다.
     */
    void modifyTag(TagModifyRequestDto request);

    /**
     * 태그 단건 조회를 하기 위한 메서드
     *
     * @param tagNo 조회하길 원하는 tag 의 식별번호를 담고있다.
     */
    TagResponseDto findTag(Integer tagNo);

    /**
     * 태그 다건 조회를 하기 위한 메서드
     *
     * @param pageable 태그 조회시에 사용되며 조회하고자하는 tag 의 page 와 size 정보를 담고 있다.
     */
    PageResponse<TagResponseDto> findTags(Pageable pageable);

    /**
     * db에 저장하기 위해 dto 를 변환시켜주는 메서드
     *
     * @param dto 태그의 등록, 수정시에 필요한 정보를 담고 있는 dto
     * @return Tag
     */
    default Tag dtoToEntity(TagAddRequestDto dto) {
        return Tag.builder()
                .title(dto.getTitle())
                .build();
    }

    /**
     * db에 저장된 테이블을 responseDto 로 변환시켜주는 메서드
     *
     * @param tag 변환시키려는 Tag 객체
     * @return TagResponseDto
     */
    default TagResponseDto entityToDto(Tag tag) {
        return TagResponseDto.builder()
                .tagNo(tag.getTagNo())
                .title(tag.getTitle())
                .registerDatetime(tag.getRegisterDatetime())
                .modifiedDatetime(tag.getModifiedDatetime())
                .build();
    }
}
