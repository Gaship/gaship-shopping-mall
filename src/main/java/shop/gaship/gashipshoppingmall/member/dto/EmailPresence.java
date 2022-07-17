package shop.gaship.gashipshoppingmall.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일이 존재하는지에 대한 결과를 담는 dto입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class EmailPresence {
    private Boolean hasEmail;
}
