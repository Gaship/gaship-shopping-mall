package shop.gaship.gashipshoppingmall.membertag.dto.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 회원이 자신이 설정한 태그들의 정보를 담을 수 있는 responseDto 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class MemberTagResponseDto {
    private final Integer tagNo;
    private final String title;

    /**
     * 태그 빌더입니다.
     *
     * @param tagNo 태그 번호
     * @param title 태그명
     */
    @Builder
    public MemberTagResponseDto(Integer tagNo, String title) {
        this.tagNo = tagNo;
        this.title = title;
    }
}
