package shop.gaship.gashipshoppingmall.inquiry.controller.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;
import shop.gaship.gashipshoppingmall.inquiry.inquiryenum.InquiryType;
import shop.gaship.gashipshoppingmall.response.PageResponse;
import shop.gaship.gashipshoppingmall.statuscode.status.ProcessStatus;

/**
 * 상품문의에 대한 독자적인 api를 처리하는 클래스입니다.
 * 현재는 조회기능만 있지만 차후 기능 확장시 해당 클래스에 상품문의관련 api 요청 처리에 대한 기능들이 추가됩니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class ProductInquiryRestController {

    private final InquiryService inquiryService;

    /**
     * 상품문의 목록을 조회하는 요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> productInquiryList(
        Pageable pageable) {
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findInquiries(pageable, InquiryType.PRODUCT_INQUIRIES.getValue());

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * 답변대기 상태인 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries/status-hold")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> productInquiryStatusHoldList(
        Pageable pageable) {
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findInquiriesByStatusCodeNo(pageable,
                InquiryType.PRODUCT_INQUIRIES.getValue(), ProcessStatus.WAITING.getValue());

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * 답변완료 상태인 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product-inquiries/status-complete")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> productInquiryStatusCompleteList(
        Pageable pageable) {
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findInquiriesByStatusCodeNo(pageable,
                InquiryType.PRODUCT_INQUIRIES.getValue(), ProcessStatus.COMPLETE.getValue());

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }


    /**
     * 특정회원에 대한 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param memberNo 기준이 되는 회원의 식별번호입니다.
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/member/{memberNo}/product-inquiries")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> productInquiryMemberList(
        Pageable pageable, @PathVariable Integer memberNo) {

        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findInquiriesByMemberNo(pageable,
                InquiryType.PRODUCT_INQUIRIES.getValue(), memberNo);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * 특정상품에 대한 상품문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param productNo 기준이 되는 상품의 식별번호입니다.
     * @param pageable  페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/product/{productNo}")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> productInquiryProductList(
        Pageable pageable, @PathVariable Integer productNo) {

        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findInquiriesByMemberNo(pageable,
                InquiryType.PRODUCT_INQUIRIES.getValue(), productNo);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }


}
