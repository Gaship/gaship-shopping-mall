package shop.gaship.gashipshoppingmall.addresslocal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 최상위 주소지를 받기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressLocalResponseDto {
    private Integer addressNo;
    private String addressName;
    private boolean allowDelivery;
}
