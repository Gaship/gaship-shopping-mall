package shop.gaship.gashipshoppingmall.productreview.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품평 조회 응답 dto입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class ProductReviewResponseDto {
    private Integer orderProductNo;
    private String writerNickName;
    private String productName;
    private String title;
    private String content;
    private String imagePath;
    private byte[] image;
    private Integer starScore;
    private LocalDateTime registerDateTime;
    private LocalDateTime modifyDateTime;
}
