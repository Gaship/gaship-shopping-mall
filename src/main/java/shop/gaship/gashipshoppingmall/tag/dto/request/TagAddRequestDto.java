package shop.gaship.gashipshoppingmall.tag.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 태그를 등록할 때 사용되는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class TagAddRequestDto {
    private String title;

    @Builder
    public TagAddRequestDto(String title) {
        this.title = title;
    }
}