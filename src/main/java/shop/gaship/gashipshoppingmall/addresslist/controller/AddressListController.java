package shop.gaship.gashipshoppingmall.addresslist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.service.AddressListService;

import javax.validation.Valid;

/**
 * 배송지목록의 restController 입니다.
 *
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class AddressListController {
    private final AddressListService addressListService;

    /**
     * 배송지목록의 Post Mapping
     *
     * @param request 배송지 목록을 등록하기 위한 정보를 담는 dto 입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 최정우
     */
    @PostMapping("/api/members/{memberId}/addressLists")
    public ResponseEntity<Void> AddressListAdd(@Valid @RequestBody AddressListAddRequestDto request) {
        addressListService.addAddressList(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 배송지목록의 Put Mapping
     *
     * @param request 배송지 목록을 수정하기 위한 정보를 담는 dto 입니다.
     *                dto 중 id 값은 배송지목록의 상태를 delete 로 만들 id 값입니다.
     *                dto 중 나머지는 새로운 배송지목록을 등록하기 위해 필요한 정보입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 최정우
     */
    @PutMapping("/api/members/{memberId}/addressLists")
    public ResponseEntity<Void> AddressListModify(@Valid @RequestBody AddressListModifyRequestDto request) {
        addressListService.modifyAddressList(request);
        addressListService.addAddressList(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 배송지목록의 Delete Mapping
     *
     * @param addressListId 배송지목록의 상태를 delete 로 만들 id 값입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 OK.
     */
    @DeleteMapping("/api/members/{memberId}/addressLists/{addressListId}")
    public ResponseEntity<Void> AddressListRemove(@PathVariable Integer addressListId) {
        addressListService.removeAddressList(addressListId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 배송지목록의 Get Mapping
     *
     * @param addressListId 배송지목록 중 조회하길 원하는 id 값입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 OK.
     */
    @GetMapping("/api/members/{memberId}/addressLists/{addressListId}")
    public ResponseEntity<AddressListResponseDto> AddressListDetails(@PathVariable Integer addressListId) {
        AddressListResponseDto addressListResponseDto = addressListService.findAddressList(addressListId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressListResponseDto);
    }

    /**
     * 배송지목록의 Get Mapping
     *
     * @param pageable 배송지목록 중 조회하길 원하는 페이지의 정보를 담고있는 매개변수입니다.
     * @return responseEntity body 는 조회하길 원하는 배송지목록 페이지정보를 담고있는 dto, 응답 status 는 OK.
     */
    @GetMapping("/api/members/{memberId}/addressLists")
    public ResponseEntity<AddressListPageResponseDto> AddressListList(@PathVariable Integer memberId, Pageable pageable) {
        AddressListPageResponseDto addressListPageResponseDto = addressListService.findAddressLists(memberId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressListPageResponseDto);
    }
}
