package shop.gaship.gashipshoppingmall.addressLocal.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 수정하려는 주소지번호와 배송가능여부를 수정하기위한 값이들어있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyAddressRequestDto {

    @Min(1)
    @NotNull(message = "지역을 입력하세요")
    private Integer localNo;
    @NotNull(message = "가능여부를 입력하세요")
    private boolean isDelivery;
}
