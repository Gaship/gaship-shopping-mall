package shop.gaship.gashipshoppingmall.elastic.service;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.elastic.documents.ElasticProduct;
import shop.gaship.gashipshoppingmall.elastic.repository.ElasticProductRepository;
import shop.gaship.gashipshoppingmall.elastic.service.impl.ElasticServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 설명작성란
 *
 * @author : 유호철
 * @since 1.0
 */

@ExtendWith(SpringExtension.class)
@Import(ElasticServiceImpl.class)
class ElasticServiceTest {

    @MockBean
    ElasticProductRepository repository;

    @Autowired
    ElasticService service;

    ElasticProduct product;

    @BeforeEach
    void setUp() {
        product = new ElasticProduct();
        ReflectionTestUtils.setField(product, "id", 1);
        ReflectionTestUtils.setField(product, "name", "test");
        ReflectionTestUtils.setField(product, "code", "c1");
    }

    @DisplayName("엘라스틱 서치 이름으로 검색 테스트")
    @Test
    void searchNameTest() {
        given(repository.findByProductName(anyString()))
            .willReturn(List.of(product));

        ElasticProduct result = service.findName("test").get(0);
        assertThat(result.getId()).isEqualTo(product.getId());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getCode()).isEqualTo(product.getCode());


        verify(repository, times(1)).findByProductName("test");
    }

    @DisplayName("엘라스틱 서치 code로 검색 테스트")
    @Test
    void searchCodeTest() {
        given(repository.findByCode(anyString()))
            .willReturn(List.of(product));

        ElasticProduct result = service.findCode("c1").get(0);
        assertThat(result.getId()).isEqualTo(product.getId());
        assertThat(result.getName()).isEqualTo(product.getName());
        assertThat(result.getCode()).isEqualTo(product.getCode());
    }
}