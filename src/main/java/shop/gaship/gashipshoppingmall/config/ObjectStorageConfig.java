package shop.gaship.gashipshoppingmall.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * object storage 관련 설정을 위한 configuration 입니다.
 *
 * @author : 김세미
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "objectstorage")
@RequiredArgsConstructor
public class ObjectStorageConfig {
    private final DataProtectionConfig dataProtectionConfig;
    private String tenantId;
    private String password;
    private String account;
    private String username;


    public String getTenantId() {
        return dataProtectionConfig.findSecretDataFromSecureKeyManager(tenantId);
    }

    public String getPassword() {
        return dataProtectionConfig.findSecretDataFromSecureKeyManager(password);
    }

    public String getAccount() {
        return dataProtectionConfig.findSecretDataFromSecureKeyManager(account);
    }

    public String getUsername() {
        return dataProtectionConfig.findSecretDataFromSecureKeyManager(username);
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
