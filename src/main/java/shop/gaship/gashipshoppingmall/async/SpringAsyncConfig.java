package shop.gaship.gashipshoppingmall.async;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Async의 스레드를 관리하기 위해서 작성한 설정클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Configuration
@EnableAsync
public class SpringAsyncConfig {

    @Bean
    public Executor BasicThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(30);
        executor.setQueueCapacity(100);
        executor.setMaxPoolSize(50);
        executor.setThreadNamePrefix("Executor-");

        return executor;
    }
}
