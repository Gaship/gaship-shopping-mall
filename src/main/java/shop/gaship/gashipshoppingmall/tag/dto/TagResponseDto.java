package shop.gaship.gashipshoppingmall.tag.dto;

import lombok.Builder;
import lombok.Data;
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
    private String title;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifiedDatetime;

    @Builder
    public TagResponseDto(String title, LocalDateTime registerDatetime, LocalDateTime modifiedDatetime) {
        this.title = title;
        this.registerDatetime = registerDatetime;
        this.modifiedDatetime = modifiedDatetime;
    }
}
