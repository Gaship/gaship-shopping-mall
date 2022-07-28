package shop.gaship.gashipshoppingmall.tag.dto.request;


import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 태그를 등록 수정할 때 사용되는 dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class TagModifyRequestDto {
    @NotNull(message = "등록하려는 태그의 tagNo 는 필수값입니다.")
    @Min(value = 1, message = "memberNo 는 1보다 작을 수 없습니다.")
    private final Integer tagNo;

    @NotNull(message = "등록하려는 태그의 title 은 필수값입니다.")
    @Length(min = 1, max = 10, message = "title 의 길이는 최소 1 이상 최대 10 이하 입니다.")
    private final String title;

    @Builder
    public TagModifyRequestDto(Integer tagNo, String title) {
        this.tagNo = tagNo;
        this.title = title;
    }
}
