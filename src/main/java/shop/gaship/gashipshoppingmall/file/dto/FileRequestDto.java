package shop.gaship.gashipshoppingmall.file.dto;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * 파일 요청 dto 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Builder
public class FileRequestDto {
    @NotNull(message = "path 는 필수 입력값입니다.")
    private String path;

    @NotNull(message = "originalName 는 필수 입력값입니다.")@NotNull
    private String originalName;

    @NotNull(message = "extension 는 필수 입력값입니다.")
    private String extension;
}
