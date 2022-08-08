package shop.gaship.gashipshoppingmall.tag.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

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
    private final LocalDateTime modifyDatetime;

    /**
     * dto 의 빌더.
     *
     * @param tagNo 태그 id
     * @param title 태그명
     * @param registerDatetime 등록시간
     * @param modifyDatetime 수정시간
     */
    @Builder
    public TagResponseDto(Integer tagNo, String title,
                          LocalDateTime registerDatetime, LocalDateTime modifyDatetime) {
        this.tagNo = tagNo;
        this.title = title;
        this.registerDatetime = registerDatetime;
        this.modifyDatetime = modifyDatetime;
    }
}
