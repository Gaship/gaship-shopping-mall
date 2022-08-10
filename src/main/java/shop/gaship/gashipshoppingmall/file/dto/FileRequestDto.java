package shop.gaship.gashipshoppingmall.file.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * 설명작성란
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Builder
public class FileRequestDto {
    @NotNull
    private String path;

    @NotNull
    private String originalName;

    @NotNull
    private String extension;
}
