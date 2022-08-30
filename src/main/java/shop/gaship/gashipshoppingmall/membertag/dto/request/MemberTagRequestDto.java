package shop.gaship.gashipshoppingmall.membertag.dto.request;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;

/**
 * 회원이 설정하기 원하는 태그들의 id 값을 담기 위한 requestDto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagRequestDto {
    @Size(min = 5, max = 5, message = "태그가 5개 선택 되지 않았습니다.")
    @NotNull(message = "태그를 선택해주세요.")
    private List<Integer> tagIds;
}
