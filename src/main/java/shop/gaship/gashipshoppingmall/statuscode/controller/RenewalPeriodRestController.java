package shop.gaship.gashipshoppingmall.statuscode.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.RenewalPeriodResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.service.StatusCodeService;

/**
 * 상태코드의 갱신기간 관련 요청을 처리합니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/renewal-period")
public class RenewalPeriodRestController {
    private final StatusCodeService statusCodeService;

    /**
     * 갱신기간 PUT Mapping
     * 회원등급의 갱신 기간 값 수정을 위한 PUT 요청 mapping.
     *
     * @param value 수정할 갱신기간 값 (String)
     * @return responseEntity body 는 가지고 있지 않으며 응답 status 는 OK.
     * @author 김세미
     */
//    @AdminAuthority
    @PutMapping(params = "value")
    public ResponseEntity<Void> renewalPeriodModify(@RequestParam Integer value) {
        statusCodeService.modifyRenewalPeriod(value);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 갱신기간 GET Mapping
     * 회원등급의 갱신 기간 값 조회 요청을 처리합니다.
     *
     * @return 갱신기간 응답 dto 를 body 가진 status OK 의 ResponseEntity 를 반환합니다.
     */
//    @AdminAuthority
    @GetMapping
    public ResponseEntity<RenewalPeriodResponseDto> renewalPeriodDetails() {

        return ResponseEntity.status(HttpStatus.OK)
                .body(statusCodeService.findRenewalPeriod());
    }
}
