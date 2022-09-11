package shop.gaship.gashipshoppingmall.inquiry.controller.customer;

import static shop.gaship.gashipshoppingmall.inquiry.inquiryenum.InquiryType.CUSTOMER_INQUIRY;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.aspact.annotation.AdminAuthority;
import shop.gaship.gashipshoppingmall.aspact.annotation.MemberValid;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryListResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.search.InquiryListSearch;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;
import shop.gaship.gashipshoppingmall.statuscode.status.ProcessStatus;
import shop.gaship.gashipshoppingmall.util.PageResponse;

/**
 * 고객문의에 대한 요청을 처리하는 클래스입니다.
 * 현재는 조회기능만 있지만 차후 기능 확장시 해당 클래스에 고객문의관련 api 요청 처리에 대한 기능들이 추가됩니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class CustomerInquiryRestController {
    private final InquiryService inquiryService;


    /**
     * 고객문의 목록을 조회하는 요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/customer-inquiries")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryList(
        @PageableDefault Pageable pageable) {

        InquiryListSearch inquiryListSearch = new InquiryListSearch(false, null, null, null);
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findCustomerInquiriesAllOrStatusComplete(pageable, inquiryListSearch);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping(value = "/customer-inquiries/prev-page")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryListPrevPage(
        @PageableDefault Pageable pageable,
        @Valid @NotNull(message = "시작 또는 끝지점 문의번호는 필수 값입니다.")
        @Min(value = 1, message = "문의 최소값은 1입니다.") Integer inquiryNo) {

        InquiryListSearch inquiryListSearch = new InquiryListSearch(false, null, null, null);
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findCustomerInquiriesAllOrStatusCompletePrevPage(pageable, inquiryNo, inquiryListSearch);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }


    @GetMapping(value = "/customer-inquiries/next-page")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryListNextPage(
        @PageableDefault Pageable pageable,
        @Valid @NotNull(message = "시작 또는 끝지점 문의번호는 필수 값입니다.")
        @Min(value = 1, message = "문의 최소값은 1입니다.") Integer inquiryNo) {

        InquiryListSearch inquiryListSearch = new InquiryListSearch(false, null, null, null);
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findCustomerInquiriesAllOrStatusCompleteNextPage(pageable, inquiryNo, inquiryListSearch);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * 답변대기 상태인 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @AdminAuthority
    @GetMapping(value = "/customer-inquiries/status-hold")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryStatusHoldList(
        @PageableDefault Pageable pageable) {
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findInquiriesByStatusCodeNo(pageable,
                CUSTOMER_INQUIRY.getValue(), ProcessStatus.WAITING.getValue());

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    @AdminAuthority
    @GetMapping(value = "/customer-inquiries/status-complete")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryStatusCompleteList(
        @PageableDefault Pageable pageable) {


        InquiryListSearch inquiryListSearch = new InquiryListSearch(false, ANSWER_COMPLETE_NO, null, null);
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findCustomerInquiriesAllOrStatusComplete(pageable, inquiryListSearch);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * 답변완료 상태인 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @AdminAuthority
    @GetMapping(value = "/customer-inquiries/status-complete/prev-page")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryStatusCompleteListPrevPage(
        @PageableDefault Pageable pageable,
        @Valid @NotNull(message = "시작 또는 끝지점 문의번호는 필수 값입니다.")
        @Min(value = 1, message = "문의 최소값은 1입니다.") Integer inquiryNo) {

        InquiryListSearch inquiryListSearch = new InquiryListSearch(false, ANSWER_COMPLETE_NO, null, null);
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findCustomerInquiriesAllOrStatusCompletePrevPage(pageable, inquiryNo, inquiryListSearch);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);
        return ResponseEntity.ok(pageResponse);
    }

    @AdminAuthority
    @GetMapping(value = "/customer-inquiries/status-complete/next-page")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryStatusCompleteListNextPage(
        @PageableDefault Pageable pageable,
        @Valid @NotNull(message = "시작 또는 끝지점 문의번호는 필수 값입니다.")
        @Min(value = 1, message = "문의 최소값은 1입니다.") Integer inquiryNo) {

        InquiryListSearch inquiryListSearch = new InquiryListSearch(false, ANSWER_COMPLETE_NO, null, null);
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findCustomerInquiriesAllOrStatusCompleteNextPage(pageable, inquiryNo,
                inquiryListSearch);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * 특정회원에 대한 고객문의목록 조회요청을 처리하는 기능입니다.
     *
     * @param memberNo 기준이 되는 회원의 식별번호입니다.
     * @param pageable 페이지네이션에 맞게 조회하기 위한 정보를 담고있는 객체입니다.
     * @return 200 status code와 함께 PageResponse에 목록들을 body로 담아서 ResponseEntity를 반환합니다.
     * @author 최겸준
     */
    @MemberValid
    @MemberAuthority
    @GetMapping(value = "/member/{memberNo}/customer-inquiries")
    public ResponseEntity<PageResponse<InquiryListResponseDto>> customerInquiryMemberList(
        @PageableDefault Pageable pageable, @PathVariable Integer memberNo) {
        Page<InquiryListResponseDto> inquiriesPage =
            inquiryService.findInquiriesByMemberNo(pageable,
                CUSTOMER_INQUIRY.getValue(), memberNo);

        PageResponse<InquiryListResponseDto> pageResponse = new PageResponse<>(inquiriesPage);

        return ResponseEntity.ok(pageResponse);
    }
}
