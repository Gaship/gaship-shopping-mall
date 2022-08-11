package shop.gaship.gashipshoppingmall.commonfile.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.util.ReflectionTestUtils;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @since 1.0
 */
@DataJpaTest
class CommonFileRepositoryTest {
    @Autowired
    CommonFileRepository commonFileRepository;

    CommonFile commonFile;

    @BeforeEach
    void setUp() {
        commonFile = CommonFile.builder()
                .path("path")
                .originalName("name")
                .extension("extension")
                .build();
        ReflectionTestUtils.setField(commonFile, "service", "product");
    }

    @DisplayName("공통파일 save 테스트")
    @Test
    void save() {
        CommonFile saved = commonFileRepository.save(commonFile);

        assertThat(saved.getPath()).isEqualTo(commonFile.getPath());
        assertThat(saved.getOriginalName()).isEqualTo(commonFile.getOriginalName());
        assertThat(saved.getExtension()).isEqualTo(commonFile.getExtension());
        assertThat(saved.getOwnerNo()).isEqualTo(commonFile.getOwnerNo());
        assertThat(saved.getService()).isEqualTo(commonFile.getService());
        assertThat(saved.getRegisterDatetime()).isNotNull();
    }

    @DisplayName("공통파일 findById 테스트")
    @Test
    void findById() {
        CommonFile saved = commonFileRepository.save(commonFile);
        CommonFile found = commonFileRepository.findById(saved.getNo()).get();

        assertThat(found.getPath()).isEqualTo(saved.getPath());
        assertThat(found.getOriginalName()).isEqualTo(saved.getOriginalName());
        assertThat(found.getExtension()).isEqualTo(saved.getExtension());
        assertThat(found.getOwnerNo()).isEqualTo(saved.getOwnerNo());
        assertThat(found.getService()).isEqualTo(saved.getService());
        assertThat(found.getRegisterDatetime()).isNotNull();
    }
}