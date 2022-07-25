package shop.gaship.gashipshoppingmall.addressLocal.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;

/**
 * 주소지정보를 위한 요청을 다루기 위한 컨트롤러입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/addressLocals")
public class AddressLocalController {
    private final AddressLocalService service;

    /**
     * put 요청이 왔을시 수정을 할수있도록 수행하는 메서드입니다.
     *
     * @param dto : 배송여부를 수정할 정보가들어있는 dto 입니다
     * @author 유호철
     */
    @PutMapping
    public ResponseEntity<Void> addressLocalModify(
        @Valid @RequestBody ModifyAddressRequestDto dto) {
        service.modifyLocalDelivery(dto);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .build();
    }


    /**
     * get 요청시 조회를 할수있는 메서드입니다.
     *
     * @param address : 검색할 주소지 입니다.
     * @return list : 검색된 주소지와 하위주소지가 담긴 리스트입니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<List<GetAddressLocalResponseDto>> addressLocalList(
        @RequestParam("address") String address) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.findAddressLocals(address));
    }
}
