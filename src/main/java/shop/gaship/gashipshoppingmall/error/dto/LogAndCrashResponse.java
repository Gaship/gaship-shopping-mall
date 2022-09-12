package shop.gaship.gashipshoppingmall.error.dto;

import lombok.Getter;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
public class LogAndCrashResponse {
    @Getter
    public static class Header {
        private Boolean isSuccessful;
        private Integer resultCode;
        private String resultMessage;
    }
}
