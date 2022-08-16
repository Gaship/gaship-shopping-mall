package shop.gaship.gashipshoppingmall.storage.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 인증 token 요청에 대한 응답 결과인 token 정보를 담은 class 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class Token {
    private String expires;
    private String id;
    private Tenant tenant;
    @JsonProperty(value = "issued_at")
    private String issuedAt;
}
