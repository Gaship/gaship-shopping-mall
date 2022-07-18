package shop.gaship.gashipshoppingmall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 요청의 결과가 실패했을떄의 결과값을 담은 클래스입니다..
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class FailureRequestDto {
    private String requestStatus;
    private String message;
}
