package shop.gaship.gashipshoppingmall.storage.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 토큰을 요청한 대상에 대응하는 tenant 정보를 담은 class 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class Tenant {
    private String description;
    private Boolean enabled;
    private String id;
    private String name;
    private String groupId;
    private String project_domain;
    private Boolean swift;
}
