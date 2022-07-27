package shop.gaship.gashipshoppingmall.tag.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 태그 단건 조회시에 필요한 값을 담는 dto입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@Getter
public class TagResponseDto {
    private final Integer tagNo;
    private final String title;
    private final LocalDateTime registerDatetime;
    private final LocalDateTime modifiedDatetime;

    @Builder
    public TagResponseDto(Integer tagNo, String title, LocalDateTime registerDatetime, LocalDateTime modifiedDatetime) {
        this.tagNo = tagNo;
        this.title = title;
        this.registerDatetime = registerDatetime;
        this.modifiedDatetime = modifiedDatetime;
    }
}
