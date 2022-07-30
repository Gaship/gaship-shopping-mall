package shop.gaship.gashipshoppingmall.inquiry.controller;

import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
import shop.gaship.gashipshoppingmall.inquiry.service.InquiryService;

/**
 * 문의에 대한 요청을 담당하는 controller입니다.
 *
 * @author 최겸준
 * @since 1.0
 */
@RestController
@RequestMapping("/api/inquiries")
@RequiredArgsConstructor
public class InquiryRestController {
    private final InquiryService inquiryService;
    public static final Integer MODIFY_NO = 0;
    public static final Boolean INQUIRY_ANSWER_ADD = Boolean.TRUE;
    public static final Boolean INQUIRY_ANSWER_MODIFY = Boolean.FALSE;

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
     * 문의의 답변을 추가 또는 수정하기 위한 요청을 처리합니다.
     *
     * @param inquiryAnswerAddRequestDto 문의답변에 들어가야할 정보들을 가지는 DTO 객체입니다.
     * @return 성공시 201인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @PutMapping("/{inquiryNo}")
    public ResponseEntity<Void> inquiryAnswerAddOrModify(
        @Valid @RequestBody InquiryAnswerRequestDto inquiryAnswerAddRequestDto) {
        if (!Objects.equals(inquiryAnswerAddRequestDto.getEmployeeNo(), MODIFY_NO)) {
            inquiryService.addOrModifyInquiryAnswer(inquiryAnswerAddRequestDto, INQUIRY_ANSWER_ADD);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }

        inquiryService.addOrModifyInquiryAnswer(inquiryAnswerAddRequestDto, INQUIRY_ANSWER_MODIFY);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 문의를 삭제하는 기능 요청을 처리합니다.
     * 이때 삭제는 실삭제를 뜻합니다.
     *
     * @param inquiryNo 삭제할 문의의 번호입니다.
     * @return 성공시 201인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping("/{inquiryNo}")
    public ResponseEntity<Void> inquiryDelete(@PathVariable Integer inquiryNo) {
        inquiryService.deleteInquiry(inquiryNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 문의의 답변을 삭제하는 기능 요청을 담당합니다.
     * 이때 삭제는 db상의 답변을 null로 변경하는 것을 의미합니다.
     * 정확하게는 문의테이블의 수정이 일어나는 것입니다.
     * 답변변경 외에도 상태와 여러가지 정보를 변경합니다.
     *
     * @param inquiryNo 답변을 삭제할 문의의 번호입니다.
     * @return 성공시 201인 statusCode, body에는 void 값을 담은 객체를 반환합니다.
     * @author 최겸준
     */
    @DeleteMapping("/{inquiryNo}/answer")
    public ResponseEntity<Void> inquiryAnswerDelete(@PathVariable Integer inquiryNo) {
        inquiryService.deleteAnswerInquiry(inquiryNo);
        return ResponseEntity.ok().build();
    }

    /**
     * 문의상태를 통해서 문의들을 조회하는 기능입니다.
     *
     * @return response entity
     * @author 최겸준
     */
    @GetMapping(value = "/customer")
    public ResponseEntity<InquiryAddRequestDto> customerInquiryList(Pageable pageable) {

        return null;
    }
}