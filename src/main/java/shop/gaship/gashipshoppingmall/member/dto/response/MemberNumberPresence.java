package shop.gaship.gashipshoppingmall.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 회원의 고유식별번호가 담긴 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class MemberNumberPresence {
    private Integer memberNo;
}
