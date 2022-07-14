package shop.gaship.gashipshoppingmall.tag.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.dto
 * fileName       : TagResponseDto
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
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
