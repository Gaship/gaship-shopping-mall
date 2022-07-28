package shop.gaship.gashipshoppingmall.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일 찾기를 완료 후 이메일 데이터 결과의 일부를 노출한 데이터를 담은 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class FindMemberEmailResponse {
    private String obscuredEmail;
}
