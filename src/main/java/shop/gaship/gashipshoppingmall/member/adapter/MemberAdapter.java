package shop.gaship.gashipshoppingmall.member.adapter;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import shop.gaship.gashipshoppingmall.config.ServerConfig;
import shop.gaship.gashipshoppingmall.error.ErrorResponse;
import shop.gaship.gashipshoppingmall.error.NoResponseDataException;
import shop.gaship.gashipshoppingmall.error.RequestFailureThrow;
import shop.gaship.gashipshoppingmall.member.dto.ReissuePasswordReceiveEmailDto;
import shop.gaship.gashipshoppingmall.member.dto.SuccessReissueResponse;

/**
 * 쇼핑몰 서버의 멤버 도메인에서 타 서비스 서버에 요청시 이용하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Component
@RequiredArgsConstructor
public class MemberAdapter {
    private final ServerConfig serverConfig;

    /**
     * Auth 서버로부터 고객의 이메일에 전송된 비밀번호와 고객의 이메일을 얻어옵니다.
     *
     * @param reissuePasswordDto Auth 서버에서 이메일을 보낼 때 필요한 데이터를 담은 객체입니다.
     * @return 이메일 전송에 성공한 후 응답으로 임시비밀번호가 담긴 객체를 반환합니다.
     */
    public ResponseEntity<SuccessReissueResponse> requestSendReissuePassword(
        ReissuePasswordReceiveEmailDto reissuePasswordDto) {
        return WebClient.create(serverConfig.getAuthUrl()).post()
            .uri("/securities/password/reissue").contentType(MediaType.APPLICATION_JSON)
            .bodyValue(reissuePasswordDto).accept(MediaType.APPLICATION_JSON).retrieve()
            .toEntity(SuccessReissueResponse.class).timeout(Duration.ofSeconds(5)).blockOptional()
            .orElseThrow(() -> new NoResponseDataException("Auth 서버로부터 응답이 없습니다."));
    }

    public void requestSendRecommendMemberCouponGenerationIssue(Integer recommendMemberNo) {
        WebClient.create(serverConfig.getCouponUrl()).post()
            .uri("/api/coupon-generations-issues/{recommendMemberNo}", recommendMemberNo)
            .retrieve()
            .onStatus(HttpStatus::isError, clientResponse ->
                clientResponse.bodyToMono(ErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(new RequestFailureThrow(errorResponse.getMessage(), clientResponse.statusCode()))))
            .bodyToMono(void.class)
            .block();
    }
}
