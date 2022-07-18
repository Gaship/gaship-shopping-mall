package shop.gaship.gashipshoppingmall.tag.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * 태그를 등록 수정할 때 사용되는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class TagRequestDto {
    private Integer tagNo;
    private String title;

    @Builder
    public TagRequestDto(Integer tagNo, String title) {
        this.tagNo = tagNo;
        this.title = title;
    }
}
