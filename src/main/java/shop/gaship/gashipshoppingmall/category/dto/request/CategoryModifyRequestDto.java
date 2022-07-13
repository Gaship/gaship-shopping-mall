package shop.gaship.gashipshoppingmall.category.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.request
 * fileName       : CategoryModifyRequest
 * author         : 김보민
 * date           : 2022-07-10
 * description    : 카테고리 수정 리퀘스트
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-10        김보민       최초 생성
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryModifyRequestDto {
    @NotNull
    private Integer no;

    @NotBlank
    private String name;

    public void updateNo(Integer no) {
        this.no = no;
    }
}
