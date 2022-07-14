package shop.gaship.gashipshoppingmall.tag.dto;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.tag.dto
 * fileName       : TagRequestDto
 * author         : choijungwoo
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        choijungwoo       최초 생성
 */
@Getter
public class TagRequestDto {
    private Integer tagNo;
    private String title;

    @Builder
    public TagRequestDto(Integer tagNo, String title) {
        this.tagNo = tagNo;
        this.title = title;
    }
}
