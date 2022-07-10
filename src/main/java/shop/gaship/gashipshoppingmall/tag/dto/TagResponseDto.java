package shop.gaship.gashipshoppingmall.tag.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class TagResponseDto {
    private Integer tagNo;
    private String title;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifiedDatetime;
}
