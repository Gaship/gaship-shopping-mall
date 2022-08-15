package shop.gaship.gashipshoppingmall.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.gaship.gashipshoppingmall.util.Events;

/**
 * 스프링 구동시에 Events에 applicationContext를 주입하기위한 설정 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class EventsConfiguration {
    private final ApplicationContext applicationContext;

    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Events.setPublisher(applicationContext);
    }
}
