package shop.gaship.gashipshoppingmall.util;

import java.util.Objects;
import org.springframework.context.ApplicationEventPublisher;
import shop.gaship.gashipshoppingmall.error.ApplicationEventPublisherNotFoundException;

/**
 * 이벤트를 실행하기위해 ApplicationEventPublisher를 주입받고 트리거 기능을 수행할수 있는 클래스입니다.
 *
 * @author : 최겸준
 * @since 1.0
 */
public class Events {
    private static ApplicationEventPublisher publisher;

    /**
     * 스프링 구동시에 ApplicationEventPublisher를 주입받는 setter입니다.
     *
     * @param publisher ApplicationContext가 전달됩니다.
     */
    public static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    /**
     * 실제 서비스로직에서 해당 메소드를 이용하여 이벤트를 시작시키는 트리거 기능입니다.
     *
     * @param event handler가 처리할때 필요한 정보를 담고 있는 클래스입니다.
     */
    public static void raise(Object event) {
        if (Objects.isNull(publisher)) {
            throw new ApplicationEventPublisherNotFoundException();
        }

        publisher.publishEvent(event);
    }
}
