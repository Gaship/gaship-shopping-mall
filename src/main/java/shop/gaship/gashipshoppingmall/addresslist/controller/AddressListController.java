package shop.gaship.gashipshoppingmall.addresslist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.service.AddressListService;

/**
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class AddressListController {
    private final AddressListService addressListService;

    @PostMapping("/members/{memberId}/addressLists")
    public ResponseEntity<Void> AddressListAdd(@RequestBody AddressListAddRequestDto request) {
        addressListService.addAddressList(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PutMapping("/members/{memberId}/addressLists")
    public ResponseEntity<Void> AddressListModify(@RequestBody AddressListModifyRequestDto request) {
        addressListService.modifyAddressList(request);
        addressListService.addAddressList(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @DeleteMapping("/members/{memberId}/addressLists/{addressListId}")
    public ResponseEntity<Void> AddressListRemove(@PathVariable Integer addressListId) {
        addressListService.removeAddressList(addressListId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping("/members/{memberId}/addressLists/{addressListId}")
    public ResponseEntity<AddressListResponseDto> AddressListDetails(@PathVariable Integer addressListId) {
        AddressListResponseDto addressListResponseDto = addressListService.findAddressList(addressListId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressListResponseDto);
    }

    @GetMapping("/members/{memberId}/addressLists")
    public ResponseEntity<AddressListPageResponseDto> AddressListList(Pageable pageable) {
        AddressListPageResponseDto addressListPageResponseDto = addressListService.findAddressLists(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressListPageResponseDto);
    }
}
