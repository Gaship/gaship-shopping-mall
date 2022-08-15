package shop.gaship.gashipshoppingmall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 내부 서버들의 URL을 관리하는 클래스입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "gaship-server")
public class ServerConfig {
    private String authUrl;
    private String paymentsUrl;
    private String schedulerUrl;
    private String couponUrl;

    public String getAuthUrl() {
        return authUrl;
    }

    public String getPaymentsUrl() {
        return paymentsUrl;
    }

    public String getSchedulerUrl() {
        return schedulerUrl;
    }

    public String getCouponUrl() {
        return couponUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public void setPaymentsUrl(String paymentsUrl) {
        this.paymentsUrl = paymentsUrl;
    }

    public void setSchedulerUrl(String schedulerUrl) {
        this.schedulerUrl = schedulerUrl;
    }

    public void setCouponUrl(String couponUrl) {
        this.couponUrl = couponUrl;
    }
}
