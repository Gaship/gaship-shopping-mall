package shop.gaship.gashipshoppingmall.addressLocal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 *packageName    : shop.gaship.gashipshoppingmall.addressLocal.dto.request
 * fileName       : ModifyAddressRequestDto
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
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
