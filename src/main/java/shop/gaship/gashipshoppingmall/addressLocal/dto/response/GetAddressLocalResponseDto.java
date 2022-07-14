package shop.gaship.gashipshoppingmall.addressLocal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *packageName    : shop.gaship.gashipshoppingmall.addressLocal.dto.response
 * fileName       : GetAddressLocalResponseDto
 * author         : 유호철
 * date           : 2022/07/14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */

@Getter
@Setter
@NoArgsConstructor
public class GetAddressLocalResponseDto {
    private String upperAddressName;
    private String addressName;

    public GetAddressLocalResponseDto(String upperAddressName, String addressName) {
        this.upperAddressName = upperAddressName;
        this.addressName = addressName;
    }
}
