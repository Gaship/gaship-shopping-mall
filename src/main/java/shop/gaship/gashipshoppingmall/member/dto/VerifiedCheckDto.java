package shop.gaship.gashipshoppingmall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일 검증이 완료된 결과를 담은 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class VerifiedCheckDto {
    private Boolean status;
}
