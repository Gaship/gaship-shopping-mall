package shop.gaship.gashipshoppingmall.addresslocal.controller;

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
import shop.gaship.gashipshoppingmall.addresslocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressSubLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.dto.response.AddressUpperLocalResponseDto;
import shop.gaship.gashipshoppingmall.addresslocal.service.AddressLocalService;

/**
 * 주소지정보를 위한 요청을 다루기 위한 컨트롤러입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/addressLocals")
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
     * get 요청시 최상위 주소지를 반환하는 메서드입니다.
     * level 1 인 주소지들이 반환된다. ex: 경상남도, 서울특별시 ...
     *
     * @return 상위 주소지들이 반환됩니다.
     */
    @GetMapping
    public ResponseEntity<List<AddressUpperLocalResponseDto>> addressLocalList() {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.findAddressLocals());
    }


    /**
     * get 요청시 조회를 할수있는 메서드입니다.
     *
     * @param address : 검색할 주소지 입니다.
     * @return list : 검색된 주소지의 하위 주소와 번호가 담겨있습니다.
     * @author 유호철
     */
    @GetMapping(params = "address")
    public ResponseEntity<List<AddressSubLocalResponseDto>> addressLocalSubList(
        @RequestParam("address") String address) {
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(service.findSubLocals(address));
    }
}
