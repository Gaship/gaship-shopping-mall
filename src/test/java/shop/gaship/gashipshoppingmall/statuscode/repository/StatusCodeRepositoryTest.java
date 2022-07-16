package shop.gaship.gashipshoppingmall.statuscode.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.statuscode.exception.StatusCodeNotFoundException;

/**
 * packageName    : shop.gaship.gashipshoppingmall.statuscode.repository
 * fileName       : StatusCodeRepositoryTest
 * author         : 유호철
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR     NOTE
 * -----------------------------------------------------------
 * 2022/07/11        유호철       최초생성
 */
@DataJpaTest
class StatusCodeRepositoryTest {

    @Autowired
    StatusCodeRepository repository;

    StatusCode statusCode;

    @BeforeEach
    void setUp() {

            statusCode = StatusCode.builder()
                .statusCodeName("test")
                .groupCodeName("tt")
                .priority(1)
                .explanation("ttt").build();

    }

    @DisplayName("공통코트 조회를 위한 테스트")
    @Test
    void statusCodeSelectTest(){
        //given
        repository.save(statusCode);

        //when & then
        StatusCode code = repository.findById(statusCode.getStatusCodeNo()).get();

        assertThat(code.getGroupCodeName()).isEqualTo(statusCode.getGroupCodeName());
        assertThat(code.hashCode()).hasSameHashCodeAs(statusCode.hashCode());
        assertThat(code).isEqualTo(statusCode);

    }

}