package shop.gaship.gashipshoppingmall.statuscode.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.gaship.gashipshoppingmall.statuscode.dto.response.StatusCodeResponseDto;
import shop.gaship.gashipshoppingmall.statuscode.service.StatusCodeService;


/**
 * 상태코드에 관한 요청을 받는 Rest 컨트롤러입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/status-codes")
public class StatusCodeRestController {
    private final StatusCodeService statusCodeService;

    /**
     * 상태코드 GET Mapping
     * 상태그룹코드명을 통해 해당 그룹에 속해 있는 상태 코드 목록을 조회하기 위한 GET 요청 mapping.
     *
     * @param groupCodeName 조회하려는 상태그룹 코드명(String)
     * @return responseEntity body 로 상태코드명과 우선순위 값을 담은 StatusCodeResponse List 를 가지며 응답 status 는 OK.
     * @author 김세미
     */
//    @MemberAuthority
    @GetMapping("/{groupCodeName}")
    public ResponseEntity<List<StatusCodeResponseDto>> statusCodeList(
        @PathVariable String groupCodeName) {

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
            .body(statusCodeService.findStatusCodes(groupCodeName));
    }
}
