package shop.gaship.gashipshoppingmall.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class RedisTest {
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    void insertGet_string() {
        redisTemplate.opsForHash().put("2", "2-2", "hihi");
        String result = redisTemplate.opsForHash().get("2", "2-2")
            .toString();
        assertThat(result).isEqualTo("hihi");
    }
}