package shop.gaship.gashipshoppingmall.tag.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 태그를 등록할 때 사용되는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class TagAddRequestDto {
    @NotNull(message = "등록하려는 태그의 title 은 필수값입니다.")
    @Length(min = 1, max = 10, message = "title 의 길이는 최소 1 이상 최대 10 이하 입니다.")
    private String title;

    @Builder
    public TagAddRequestDto(String title) {
        this.title = title;
    }
}
