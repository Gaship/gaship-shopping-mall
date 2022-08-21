package shop.gaship.gashipshoppingmall.addresslocal.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 주소지를 반환받기위한 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
@Setter
@NoArgsConstructor
public class GetAddressLocalResponseDto {
    private String upperAddressName;
    private String addressName;

    private Integer addressNo;

    private boolean isDelivery;

    public GetAddressLocalResponseDto(String upperAddressName, String addressName) {
        this.upperAddressName = upperAddressName;
        this.addressName = addressName;
    }
}
