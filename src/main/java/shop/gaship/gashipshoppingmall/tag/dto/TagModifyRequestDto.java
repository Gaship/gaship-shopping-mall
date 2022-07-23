package shop.gaship.gashipshoppingmall.tag.dto;


import lombok.Builder;
import lombok.Getter;

/**
 * 태그를 등록 수정할 때 사용되는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class TagModifyRequestDto {
    private final Integer tagNo;
    private final String title;

    @Builder
    public TagModifyRequestDto(Integer tagNo, String title) {
        this.tagNo = tagNo;
        this.title = title;
    }
}
