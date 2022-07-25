package shop.gaship.gashipshoppingmall.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
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
@ExtendWith({SpringExtension.class})
@EnableConfigurationProperties(
    value = {DataSourceConfig.class, DataProtectionConfig.class})
@TestPropertySource({
    "classpath:application-dev.properties",
    "classpath:application.properties"
})
public class DataSourceConfigTest {
    @Autowired
    DataSourceConfig dataSourceConfig;

    @Test
    void dataSourceConfigTest() throws SQLException {
//        assertThat(dataSourceConfig.getDataSource(null)
//            .getConnection().isClosed()).isFalse();

        assertThat(dataSourceConfig.getDriverClassName())
            .isEqualTo("com.mysql.cj.jdbc.Driver");
        assertThat(dataSourceConfig.getUrl())
            .isEqualTo("e67723aa5f3840ada297c5e6ee94799e");
        assertThat(dataSourceConfig.getUsername())
            .isEqualTo("gaship");
        assertThat(dataSourceConfig.getPassword())
            .isEqualTo("876b9add24b943869830b1919a7525ab");

    }
}
