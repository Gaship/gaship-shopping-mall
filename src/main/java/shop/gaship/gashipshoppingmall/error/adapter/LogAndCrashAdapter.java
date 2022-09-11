package shop.gaship.gashipshoppingmall.error.adapter;

import java.time.Duration;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipshoppingmall.error.dto.LogAndCrashDto;
import shop.gaship.gashipshoppingmall.error.dto.LogAndCrashResponse;

/**
 * NHN 클라우드 로그 앤 크래시를 적용하는 어댑터입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
public class LogAndCrashAdapter {
    @Value("${log-and-crash.url}")
    private String url;

    @Value("${log-and-crash.appkey}")
    private String appKey;

    public void requestSendReissuePassword(
        Throwable errorClass, String message) {
        String bodyMessage = "Gaship-shopping-error -" + errorClass.getClass().getName() + " : " + message;
        String projectVersion = "1.0.0";
        String logVersion = "v2";
        String logSource = "http";
        String logType = "nolo2-log";
        String logLevel = "ERROR";

        WebClient.create(url).post()
            .uri("/v2/log")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new LogAndCrashDto(appKey, projectVersion, logVersion, bodyMessage,
                logSource, logType, logLevel))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatus::isError, ClientResponse::createException)
            .toEntity(LogAndCrashResponse.class)
            .block();
    }
}
