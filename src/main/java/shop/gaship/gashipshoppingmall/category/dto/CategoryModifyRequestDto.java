package shop.gaship.gashipshoppingmall.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryModifyRequestDto {

    @NotBlank
    private String name;
}
