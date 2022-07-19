package shop.gaship.gashipshoppingmall.membertag.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 회원이 설정하기 원하는 태그들의 id값을 담기 위한 requestDto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagRequestDto {
    private final Integer memberNo;
    private final List<Integer> tagIds;

    /**
     * requestDto 의 builder 입니다.
     *
     * @param memberNo the member no
     * @param tagIds   the tag ids
     */
    @Builder
    public MemberTagRequestDto(Integer memberNo, List<Integer> tagIds) {
        this.memberNo = memberNo;
        this.tagIds = tagIds;
    }
}
