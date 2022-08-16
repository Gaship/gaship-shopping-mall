package shop.gaship.gashipshoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 가십쇼핑몰 api를 실행하는 실행부입니다.
 *
 * @author 조재철
 * @author 최겸준
 * @author 유호철
 * @author 최정우
 * @author 김세미
 * @author 김보민
 * @author 김민수
 */
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class GashipShoppingMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(GashipShoppingMallApplication.class, args);
    }
}
