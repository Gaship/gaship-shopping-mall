package shop.gaship.gashipshoppingmall.addressLocal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.AddressSearchRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.request.ModifyAddressRequestDto;
import shop.gaship.gashipshoppingmall.addressLocal.dto.response.GetAddressLocalResponseDto;
import shop.gaship.gashipshoppingmall.addressLocal.service.AddressLocalService;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<Void> addressLocalModify(@Valid @RequestBody ModifyAddressRequestDto dto) {
        service.modifyLocalDelivery(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }


    /**
     * get 요청시 조회를 할수있는 메서드입니다.
     *
     * @param dto : 검색할 주소지가 담긴 dto 입니다.
     * @return list : 검색된 주소지와 하위주소지가 담긴 리스트입니다.
     * @author 유호철
     */
    @GetMapping
    public ResponseEntity<List<GetAddressLocalResponseDto>> addressLocalList(@Valid @RequestBody AddressSearchRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findAddressLocals(dto));
    }
}
