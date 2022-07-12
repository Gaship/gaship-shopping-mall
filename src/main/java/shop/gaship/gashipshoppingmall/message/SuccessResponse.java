package shop.gaship.gashipshoppingmall.message;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : shop.gaship.gashipshoppingmall
 * fileName       : SuccessResponse
 * author         : 김보민
 * date           : 2022-07-12
 * description    : 성공 시 보낼 response body
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-12        김보민       최초 생성
 */
@Getter
@Setter
public class SuccessResponse {
    private LocalDateTime responseDateTime;
    private Object data;

    private SuccessResponse(Object data) {
        this.responseDateTime = LocalDateTime.now();
        this.data = data;
    }

    public static SuccessResponse data(Object data) {
        return new SuccessResponse(data);
    }

    public static SuccessResponse noData() {
        return new SuccessResponse(null);
    }
}
