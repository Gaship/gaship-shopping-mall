package shop.gaship.gashipshoppingmall.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 같은 닉네임이 존재하는지에 대한 결과 여부를 가진 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class NicknamePresence {
    private Boolean hasNickname;
}
