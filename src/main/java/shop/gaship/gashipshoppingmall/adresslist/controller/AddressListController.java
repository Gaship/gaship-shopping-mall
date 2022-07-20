package shop.gaship.gashipshoppingmall.adresslist.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.gaship.gashipshoppingmall.adresslist.dto.AddressListPageResponseDto;
import shop.gaship.gashipshoppingmall.adresslist.dto.AddressListRequestDto;
import shop.gaship.gashipshoppingmall.adresslist.dto.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.adresslist.service.AddressListService;

/**
 * @author 최정우
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
public class AddressListController {
    private final AddressListService addressListService;

    @PostMapping("/members/{memberId}/addressLists")
    public ResponseEntity<Void> AddressListAdd(@RequestBody AddressListRequestDto request) {
        addressListService.addAddressList(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @PutMapping("/members/{memberId}/addressLists")
    public ResponseEntity<Void> AddressListModify(@RequestBody AddressListRequestDto request) {
        addressListService.modifyAddressList(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @DeleteMapping("/members/{memberId}/addressLists/{addressListId}")
    public ResponseEntity<Void> AddressListRemove(@PathVariable Integer addressListId) {
        addressListService.deleteAddressList(addressListId);

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
    public ResponseEntity<AddressListPageResponseDto> AddressListList() {
        AddressListPageResponseDto addressListPageResponseDto = addressListService.findAddressLists();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressListPageResponseDto);
    }
}
