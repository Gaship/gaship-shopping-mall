package shop.gaship.gashipshoppingmall.membertag.dto;

import lombok.Builder;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

/**
 * 회원이 자신이 설정한 태그들의 정보를 담을 수 있는 responseDto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagResponseDto {
    private final Tag tag;

    /**
     * responseDto 의 builder 입니다.
     *
     * @param tag the tag
     */
    @Builder
    public MemberTagResponseDto(Tag tag) {
        this.tag = tag;
    }
}
