package shop.gaship.gashipshoppingmall.config;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * 설명작성란
 *
 * @author 김민수
 * @since 1.0
 */
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value= ServerConfig.class)
@TestPropertySource("classpath:application-prod.properties")
public class ServerConfigTest {

    @Autowired
    ServerConfig config;

    @Test
    void serverConfigEnvironmentTest() {
        assertThat(config.getAuthUrl())
            .isEqualTo("http://133.186.132.244:7071");
        assertThat(config.getPaymentsUrl())
            .isEqualTo("http://133.186.132.244:7073");
        assertThat(config.getSchedulerUrl())
            .isEqualTo("http://133.186.132.244:7074");
    }
}
