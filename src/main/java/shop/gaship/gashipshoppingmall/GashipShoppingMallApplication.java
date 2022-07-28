package shop.gaship.gashipshoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class GashipShoppingMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(GashipShoppingMallApplication.class, args);
    }
}
