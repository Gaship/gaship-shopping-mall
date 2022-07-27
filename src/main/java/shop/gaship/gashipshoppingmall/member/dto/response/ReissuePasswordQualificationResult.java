package shop.gaship.gashipshoppingmall.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 임시 비밀번호 재발급 자격 여부에 대한 결과를 가진 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReissuePasswordQualificationResult {
    private Boolean qualified;
}
