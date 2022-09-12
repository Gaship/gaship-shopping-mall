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
import shop.gaship.gashipshoppingmall.member.dto.VerifiedCheckDto;

/**
 * 쇼핑몰 서버의 멤버 도메인에서 타 서비스 서버에 요청시 이용하는 클래스입니다.
 *
 * @author 김민수
 * @author 최겸준
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
     * @author 김민수
     */
    public ResponseEntity<SuccessReissueResponse> requestSendReissuePassword(
        ReissuePasswordReceiveEmailDto reissuePasswordDto) {
        return WebClient.create(serverConfig.getAuthUrl()).post()
            .uri("/securities/password/reissue")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(reissuePasswordDto)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(SuccessReissueResponse.class).timeout(Duration.ofSeconds(5)).blockOptional()
            .orElseThrow(() -> new NoResponseDataException("Auth 서버로부터 응답이 없습니다."));
    }

    /**
     * 쿠폰 서버에 추천한 회원에 대해 추천인쿠폰을 생성 및 발급해달라고 요청합니다.
     *
     * @param recommendMemberNo 추천한 회원의 번호입니다.
     * @author 최겸준
     */
    public void requestSendRecommendMemberCouponGenerationIssue(Integer recommendMemberNo) {
        WebClient.create(serverConfig.getCouponUrl()).post()
            .uri("/api/coupons/coupon-generations-issues/{recommendMemberNo}", recommendMemberNo)
            .retrieve()
            .onStatus(HttpStatus::isError, clientResponse ->
                clientResponse.bodyToMono(ErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(new RequestFailureThrow(errorResponse.getMessage(), clientResponse.statusCode()))))
            .bodyToMono(void.class)
            .block();
    }


    /**
     * 인증번호를 회원 가입 때 확인하는 메서드입니다.
     *
     * @param verifyCode 회원이 가입 당시 인증받았던 인증번호입니다.
     * @author 최겸준
     */
    public VerifiedCheckDto checkVerifiedEmail(String verifyCode) {
        return WebClient.create(serverConfig.getAuthUrl()).get()
            .uri("/securities/verify/email/{verifyCode}", verifyCode)
            .retrieve()
            .onStatus(HttpStatus::isError, clientResponse ->
                clientResponse.bodyToMono(ErrorResponse.class)
                    .flatMap(errorResponse -> Mono.error(new RequestFailureThrow(errorResponse.getMessage(), clientResponse.statusCode()))))
            .bodyToMono(VerifiedCheckDto.class)
            .block();
    }
}
