package shop.gaship.gashipshoppingmall.addresslocal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 하위주소를 반환하기위한 메서드입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddressSubLocalResponseDto {
    private Integer addressNo;
    private String addressName;
    private Boolean isDelivery;
}
