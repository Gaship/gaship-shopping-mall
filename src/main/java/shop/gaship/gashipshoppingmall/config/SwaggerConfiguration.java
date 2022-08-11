package shop.gaship.gashipshoppingmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger REST API document를 제공하기 위한 설정 클래스입니다.
 *
 * @author 조재철
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    /**
     * api 데모를 보여주기위한 설정을하는 스프링 빈입니다.
     *
     * @return swagger 문서화 객체를 반환합니다.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("shop.gaship.gashipshoppingmall"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Open Api Test with Swagger")
                .description("설명 부분")
                .version("1.0.0")
                .build();
    }
}
