package shop.gaship.gashipshoppingmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "shop.gaship.gashipshoppingmall")
public class GashipShoppingMallApplication {
	public static void main(String[] args) {
		SpringApplication.run(GashipShoppingMallApplication.class, args);
	}

}
