package shop.gaship.gashipshoppingmall.addresslist.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.addresslist.dto.request.AddressListAddRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.request.AddressListModifyRequestDto;
import shop.gaship.gashipshoppingmall.addresslist.dto.response.AddressListResponseDto;
import shop.gaship.gashipshoppingmall.addresslist.service.AddressListService;
import shop.gaship.gashipshoppingmall.aspact.anntation.MemberOnlyAuthority;
import shop.gaship.gashipshoppingmall.util.PageResponse;

import java.util.List;

/**
 * 배송지목록의 restController 입니다.
 *
 * @author 최정우
 * @author 김세미
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(("/api/members/{memberId}/addressLists"))
public class AddressListController {
    private final AddressListService addressListService;


    /**
     * 배송지목록의 등록을 위한 Post Mapping입니다.
     *
     * @param request 배송지 목록을 등록하기 위한 정보를 담는 dto 입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 최정우
     */
    @MemberOnlyAuthority
    @PostMapping
    public ResponseEntity<Void> addressListAdd(
            @RequestBody @Valid AddressListAddRequestDto request) {
        addressListService.addAddressList(request);

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 배송지목록의 수정을 위한 Put Mapping입니다.
     *
     * @param request 배송지 목록을 수정하기 위한 정보를 담는 dto 입니다.
     *                dto 중 id 값은 배송지목록의 상태를 delete 로 만들 id 값입니다.
     *                dto 중 나머지는 새로운 배송지목록을 등록하기 위해 필요한 정보입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 CREATED.
     * @author 최정우
     */
    @MemberOnlyAuthority
    @PutMapping("/{addressListNo}")
    public ResponseEntity<Void> addressListModifyAndAdd(
            @RequestBody @Valid AddressListModifyRequestDto request) {
        addressListService.modifyAndAddAddressList(request);

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    /**
     * 배송지목록의 삭제를 위한 Delete Mapping입니다.
     *
     * @param addressListId 배송지목록의 상태를 delete 로 만들 id 값입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 OK.
     */
    @MemberOnlyAuthority
    @DeleteMapping("/{addressListId}")
    public ResponseEntity<Void> addressListRemove(@PathVariable Integer addressListId) {
        addressListService.removeAddressList(addressListId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
    }

    /**
     * 배송지목록의 단건 조회를 위한 Get Mapping입니다.
     *
     * @param addressListId 배송지목록 중 조회하길 원하는 id 값입니다.
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 OK.
     */
    @MemberOnlyAuthority
    @GetMapping("/{addressListId}")
    public ResponseEntity<AddressListResponseDto> addressListDetails(
            @PathVariable Integer addressListId) {
        AddressListResponseDto addressListResponseDto =
                addressListService.findAddressList(addressListId);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(addressListResponseDto);
    }

    /**
     * 배송지목록의 다건 조회를 위한 Get Mapping입니다.
     *
     * @param pageable 배송지목록 중 조회하길 원하는 페이지의 정보를 담고있는 매개변수입니다.
     * @return responseEntity body 는 조회하길 원하는 배송지목록 페이지정보를 담고있는 dto, 응답 status 는 OK.
     */
    @MemberOnlyAuthority
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<PageResponse<AddressListResponseDto>> addressListList(
            @PathVariable Integer memberId, Pageable pageable) {
        PageResponse<AddressListResponseDto> pageResponse =
                addressListService.findAddressLists(memberId, pageable);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(pageResponse);
    }

    @MemberOnlyAuthority
    @GetMapping
    public ResponseEntity<List<AddressListResponseDto>> addressListAll(
            @PathVariable Integer memberId) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(addressListService.findAllAddressList(memberId));
    }
}
