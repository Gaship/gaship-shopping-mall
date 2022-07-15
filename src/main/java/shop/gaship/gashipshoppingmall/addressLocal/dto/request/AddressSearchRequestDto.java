package shop.gaship.gashipshoppingmall.addressLocal.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.dto.request
 * fileName       : AddressSearchRequestDto
 * author         : 유호철
 * date           : 2022/07/14
 * description    : 주소 검색을 하기위한 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/14       유호철       최초 생성
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressSearchRequestDto {
    private String address;
}
