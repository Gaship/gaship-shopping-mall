package shop.gaship.gashipshoppingmall.config;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import shop.gaship.gashipshoppingmall.dataprotection.dto.SecureKeyResponse;
import shop.gaship.gashipshoppingmall.dataprotection.exception.NotFoundDataProtectionReposeData;

/**
 * 서버의 환경설정을 수행하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
public class DataProtectionConfig {
    @Value("${secure.keymanager.url}")
    private String secureKeyUrl;

    @Value("${secure.keymanager.appkey}")
    private String appKey;

    @Value("${secure.keymanager.userInfoProtectionKey}")
    private String userInfoProtectionKey;

    @Bean
    public String userInformationProtectionValue(){
        return Objects.requireNonNull(WebClient.create(secureKeyUrl).get()
                .uri("/keymanager/v1.0/appkey/{appkey}/secrets/{keyid}", appKey, userInfoProtectionKey)
                .retrieve()
                .toEntity(SecureKeyResponse.class)
                .blockOptional()
                .orElseThrow(() -> new NotFoundDataProtectionReposeData("응답 결과가 없습니다."))
                .getBody())
            .getBody()
            .getSecret();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
