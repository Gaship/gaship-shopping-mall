package shop.gaship.gashipshoppingmall.tag.dto;

import lombok.Builder;
import lombok.Data;
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
@Data
@Builder
public class TagResponseDto {
    private Integer tagNo;
    private String title;
    private LocalDateTime registerDatetime;
    private LocalDateTime modifiedDatetime;
}
