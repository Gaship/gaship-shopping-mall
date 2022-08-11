package shop.gaship.gashipshoppingmall.config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis의 연결과 RedisTemplate를 등록하기 위한 설정파일입니다.
 *
 * @author 김민수
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfig implements BeanClassLoaderAware {
    private String host;
    private int port;
    private String password;
    private int database;
    private ClassLoader classLoader;

    /**
     * Redis와 연결하기위한 스프링 빈입니다.
     *
     * @param dataProtectionConfig dataProtectionConfig에서 레디스의 연결 정보를 얻어 옵니다.
     * @return Redis의 연결정보 객체를 반환합니다.
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(
        DataProtectionConfig dataProtectionConfig) {
        String secretPassword =
            dataProtectionConfig.findSecretDataFromSecureKeyManager(this.password);
        String secretHost = dataProtectionConfig.findSecretDataFromSecureKeyManager(this.host);

        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(secretHost);
        configuration.setPort(port);
        configuration.setPassword(secretPassword);
        configuration.setDatabase(database);

        return new LettuceConnectionFactory(configuration);
    }

    /**
     * RedisTemplate의 기본설정과 레디스의 인메모리 저장소에 접근하기 위한 스프링빈입니다.
     *
     * @return 기본설정이 설정된 레디스 템플릿을 반환합니다.
     */
    @SuppressWarnings("java:S1452") // 레디스의 key value의 타입을 자유롭게 지정하기위함.
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory(null));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}
