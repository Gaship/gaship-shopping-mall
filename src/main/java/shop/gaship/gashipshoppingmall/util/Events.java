package shop.gaship.gashipshoppingmall.util;

import java.util.Objects;
import org.springframework.context.ApplicationEventPublisher;
import shop.gaship.gashipshoppingmall.error.ApplicationEventPublisherNotFoundException;

/**
 * @author : 최겸준
 * @since 1.0
 */
public class Events {
    private static ApplicationEventPublisher publisher;

    public static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if (Objects.isNull(publisher)) {
            throw new ApplicationEventPublisherNotFoundException();
        }

        publisher.publishEvent(event);
    }
}
