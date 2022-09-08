package shop.gaship.gashipshoppingmall.daylabor.dto.request;

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
    @Min(value = 1, message = "localNo 는 0 이하일 수 없습니다.")
    @NotNull(message = "지역번호를 입력하세요")
    private Integer localNo;

    @NotNull(message = "최대물량을 기입하세요")
    private Integer maxLabor;

    public CreateDayLaborRequestDto(Integer localNo, Integer maxLabor) {
        this.localNo = localNo;
        this.maxLabor = maxLabor;
    }
}
