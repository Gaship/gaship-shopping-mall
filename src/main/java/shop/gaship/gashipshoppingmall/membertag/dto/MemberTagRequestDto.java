package shop.gaship.gashipshoppingmall.membertag.dto;

import lombok.Builder;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import java.util.List;
import java.util.Set;

/**
 * @author 최정우
 * @since 1.0
 *
 */
@Getter
public class MemberTagRequestDto {
    private final Integer memberNo;
    private final List<Integer> tagIds;

    @Builder
    public MemberTagRequestDto(Integer memberNo, List<Integer> tagIds) {
        this.memberNo = memberNo;
        this.tagIds = tagIds;
    }
}
