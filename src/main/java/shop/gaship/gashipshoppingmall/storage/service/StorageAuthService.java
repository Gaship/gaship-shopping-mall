package shop.gaship.gashipshoppingmall.storage.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shop.gaship.gashipshoppingmall.config.ObjectStorageConfig;
import shop.gaship.gashipshoppingmall.storage.dto.request.AuthTokenRequestDto;
import shop.gaship.gashipshoppingmall.storage.dto.response.AuthTokenResponseDto;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class StorageAuthService {
    private final ObjectStorageConfig config;
    private final RestTemplate restTemplate;
    private static final String AUTH_URL =
            "https://api-identity.infrastructure.cloud.toast.com/v2.0";

    public String requestToken() {
        String identityUrl = AUTH_URL + "/tokens";

        // 헤더 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<AuthTokenRequestDto> httpEntity
                = new HttpEntity<>(createTokenRequest(), headers);

        // 토큰 요청
        ResponseEntity<AuthTokenResponseDto> response
                = restTemplate.exchange(
                        identityUrl, HttpMethod.POST, httpEntity, AuthTokenResponseDto.class);

        return Objects.requireNonNull(response.getBody()).getAccess().getToken().getId();
    }

    private AuthTokenRequestDto createTokenRequest() {
        AuthTokenRequestDto tokenRequest = new AuthTokenRequestDto();
        tokenRequest.getAuth().setTenantId(config.getTenantId());
        tokenRequest.getAuth().getPasswordCredentials().setUsername(config.getUsername());
        tokenRequest.getAuth().getPasswordCredentials().setPassword(config.getPassword());
        return tokenRequest;
    }
}
