package shop.gaship.gashipshoppingmall.dayLabor.dto.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : shop.gaship.gashipshoppingmall.dayLabor.dto.request
 * fileName       : CreateDayLaborRequestDto
 * author         : 유호철
 * date           : 2022/07/13
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/13       유호철       최초생성
 */
@Getter
@NoArgsConstructor
public class CreateDayLaborRequestDto {
    @NotNull
    private Integer localNo;
    @NotNull
    private Integer maxLabor;

    public CreateDayLaborRequestDto(Integer localNo, Integer maxLabor) {
        this.localNo = localNo;
        this.maxLabor = maxLabor;
    }
}
