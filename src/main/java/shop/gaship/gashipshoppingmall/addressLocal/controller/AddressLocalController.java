package shop.gaship.gashipshoppingmall.addressLocal.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.AddressSearchRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;

/**
 * packageName    : shop.gaship.gashipshoppingmall.addressLocal.controller
 * fileName       : AddressLocalController
 * author         : 유호철
 * date           : 2022/07/12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/12        유호철       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/addressLocals")
public class AddressLocalController {
    private final AddressLocalService service;

    /**
     * methodName : addressLocalModify
     * author : 유호철
     * description : 배송일자 수정을 위한 메서드
     *
     * @param dto ModifyAddressRequestDto
     */
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    void addressLocalModify(@Valid @RequestBody ModifyAddressRequestDto dto){
        service.modifyLocalDelivery(dto);
    }
    /**
     * methodName : getAddressLocal
     * author : 유호철
     * description : 주소지로 주소지를 조회하기위한 메서드
     *
     * @param dto AddressSearchRequestDto
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<GetAddressLocalResponseDto> getAddressLocal(@Valid @RequestBody AddressSearchRequestDto dto) {
        return service.searchAddress(dto);
    }
}
