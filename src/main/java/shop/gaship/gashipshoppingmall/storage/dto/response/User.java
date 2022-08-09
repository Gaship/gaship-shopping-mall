package shop.gaship.gashipshoppingmall.storage.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * token 을 요청한 사용자 정보를 담는 class 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class User {
    private String id;
    private String name;
}
