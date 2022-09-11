package shop.gaship.gashipshoppingmall.error;

import lombok.Getter;

/**
 * 에러를 반환하기위한 객체입니다.
 *
 * @author : 유호철
 * @since 1.0
 */

@Getter
public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
