package shop.gaship.gashipshoppingmall.addressLocal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * 검색하고싶은 주소지가 들어있는 클래스입니다.
 *
 * @author : 유호철
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressSearchRequestDto {
    private String address;
}
