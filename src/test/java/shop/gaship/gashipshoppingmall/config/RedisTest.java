package shop.gaship.gashipshoppingmall.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnableConfigurationProperties(
    value = {DataProtectionConfig.class, DataSourceConfig.class})
@TestPropertySource(
    value = {"classpath:application.properties", "classpath:application-dev.properties"})
class RedisTest {
    @Autowired
    RedisConfig config;

    @Test
    void redisConfigEnvironmentTest() {
        assertThat(config.getHost())
            .isEqualTo("a93c53427dd84868bdc3402d94270bf4");
        assertThat(config.getPort())
            .isEqualTo(6379);
        assertThat(config.getPassword())
            .isEqualTo("c61df19084544df5a11f5c9a57abb6c2");
        assertThat(config.getDatabase())
            .isEqualTo(27);
    }
}
