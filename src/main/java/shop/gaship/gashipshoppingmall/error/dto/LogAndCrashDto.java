package shop.gaship.gashipshoppingmall.error.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Log and Crash에 에러 데이터를 보낼 dto 객체입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogAndCrashDto {
    private String projectName;
    private String projectVersion;
    private String logVersion;
    private String body;
    private String logSource;
    private String logType;
    private String logLevel;
}
