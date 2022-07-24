package shop.gaship.gashipshoppingmall.config;

import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mysql 데이터 소스를 불러오는 설정입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "datasource")
public class DataSourceConfig {
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    /**
     * 데이터 베이스를 연결하는 메서드입니다.
     *
     * @param dataProtectionConfig 데이터베이스의 정보가 담긴 객체입니다.
     * @return 데이터베이스와 연결 후 연결된 객체를 반환합니다.
     */
    @Bean
    public DataSource getDataSource(DataProtectionConfig dataProtectionConfig) {
        String secretUrl = dataProtectionConfig.findSecretDataFromSecureKeyManager(url);
        String secretPassword = dataProtectionConfig.findSecretDataFromSecureKeyManager(password);

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(secretUrl);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(secretPassword);

        return dataSourceBuilder.build();
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
