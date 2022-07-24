package shop.gaship.gashipshoppingmall.membertag.dto.response;

import lombok.Builder;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.membertag.entity.MemberTag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원이 설정한 태그들의 식별번호를 담고있는 Dto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagResponseDto {
    private final List<Integer> tagNoList;

    public MemberTagResponseDto(List<MemberTag> list) {
        tagNoList = list.stream().map(MemberTag::getMemberTagNo).collect(Collectors.toList());
    }
}
