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
@EnableConfigurationProperties(value = DataProtectionConfig.class)
@TestPropertySource("classpath:application.properties")
class DataProtectionConfigTest {
    @Autowired
    DataProtectionConfig config;

    @Test
    void dataProtectionConfigTest() {
        assertThat(config.getUrl())
            .isEqualTo("https://api-keymanager.cloud.toast.com");
        assertThat(config.getAppKey())
            .isEqualTo("mcXcTmrnbRezlila");
        assertThat(config.getUserInfoProtectionKey())
            .isEqualTo("8174a0ef88454634911b8aa84988ae12");
    }
}
