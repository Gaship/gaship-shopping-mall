package shop.gaship.gashipshoppingmall.addressLocal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 *
 * 수정하려는 주소지번호와 배송가능여부를 수정하기위한 값이들어있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyAddressRequestDto {
    @NotNull
    private Integer localNo;
    @NotNull
    private boolean isDelivery;
}
