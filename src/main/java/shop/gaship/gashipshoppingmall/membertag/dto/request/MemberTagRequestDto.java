package shop.gaship.gashipshoppingmall.membertag.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 회원의 식별번호와 설정하고자하는 태그들의 식별번호를 담고있는 Dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagRequestDto {
    private final Integer memberNo;
    private final List<Integer> tagNoList;

    @Builder
    public MemberTagRequestDto(Integer memberNo, List<Integer> tagNoList) {
        this.memberNo = memberNo;
        this.tagNoList = tagNoList;
    }
}
