package shop.gaship.gashipshoppingmall.inquiry.controller.common;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAddRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.request.InquiryAnswerRequestDto;
import shop.gaship.gashipshoppingmall.inquiry.dto.response.InquiryDetailsResponseDto;
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;

/**
 * 상품문의와 고객문의에 대한 공통적인 처리 요청을 담당하는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class CommonInquiryRestController {
    private final InquiryService inquiryService;

    /**
     * 문의를 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryDto 해당 server로 온 요청을 바인딩하기위한 DTO 객체입니다.
     * @return 성공시 201인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @PostMapping
    public ResponseEntity<Void> inquiryAdd(@Valid @RequestBody InquiryAddRequestDto inquiryDto) {
        inquiryService.addInquiry(inquiryDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 문의의 답변을 추가하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerAddRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return 성공시 201인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @PostMapping("/inquiry-answer")
    public ResponseEntity<Void> inquiryAnswerAdd(
        @Valid @RequestBody InquiryAnswerRequestDto inquiryAnswerAddRequestDto) {
        inquiryService.addInquiryAnswer(inquiryAnswerAddRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 문의의 답변을 수정하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerModifyRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return 성공시 200인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @PutMapping("/{inquiryNo}/inquiry-answer")
    public ResponseEntity<Void> inquiryAnswerModify(
        @Valid @RequestBody InquiryAnswerRequestDto inquiryAnswerModifyRequestDto) {
        inquiryService.modifyInquiryAnswer(inquiryAnswerModifyRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 본인이 본인의 문의를 삭제하는 기능 요청을 처리합니다.
     * 이때 삭제는 실삭제를 뜻합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return 성공시 200인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/{inquiryNo}", params = "memberNo")
    public ResponseEntity<Void> inquiryDelete(@PathVariable Integer inquiryNo, Integer memberNo) {
        inquiryService.deleteInquiry(inquiryNo, memberNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 직원 또는 관리자가 문의를 삭제하는 기능 요청을 처리합니다.
     * 이때 삭제는 실삭제를 뜻합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return 성공시 200인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping(value = "/{inquiryNo}/manager")
    public ResponseEntity<Void> inquiryDeleteManager(@PathVariable Integer inquiryNo) {
        inquiryService.deleteInquiryManager(inquiryNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 문의의 답변을 삭제하는 기능 요청을 담당합니다.
     * 이때 삭제는 db상의 답변을 null로 변경하는 것을 의미합니다.
     * 정확하게는 문의테이블의 수정이 일어나는 것입니다.
     * 답변변경 외에도 상태와 여러가지 정보를 변경합니다.
     *
     * @param inquiryNo 답변을 삭제할 문의의 번호입니다.
     * @return 성공시 200인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping("/{inquiryNo}/inquiry-answer")
    public ResponseEntity<Void> inquiryAnswerDelete(@PathVariable Integer inquiryNo) {
        inquiryService.deleteInquiryAnswer(inquiryNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 문의 상세조회 요청을 처리하는 기능입니다.
     *
     * @param inquiryNo 조회의 기준이 되는 문의번호입니다.
     * @return ResponseEntity body에 InquiryDetailsResponseDto라는 상세조회에 필요한 정보가 담긴 객체를 넣어서 반환합니다.
     * @author 최겸준
     */
    @GetMapping(value = "/{inquiryNo}")
    public ResponseEntity<InquiryDetailsResponseDto> inquiryDetails(
        @PathVariable Integer inquiryNo) {

        return ResponseEntity.ok(inquiryService.findInquiry(inquiryNo));
    }
}
