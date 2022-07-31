package shop.gaship.gashipshoppingmall.dayLabor.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 지역별물량을 생성하기위해 요청하는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class CreateDayLaborRequestDto {
    @Min(1)
    @NotNull(message = "지역번호를 입력하세요")
    private Integer localNo;
    @NotNull(message = "최대물량을 기입하세 요")
    private Integer maxLabor;

    public CreateDayLaborRequestDto(Integer localNo, Integer maxLabor) {
        this.localNo = localNo;
        this.maxLabor = maxLabor;
    }
}
