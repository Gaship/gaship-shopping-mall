package shop.gaship.gashipshoppingmall.productreview.event;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;

/**
 * 상품평 저장, 수정 이벤트입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
public class ProductReviewSaveUpdateEvent {
    @Setter
    private String imagePath;

    private final List<CommonFile> beforeImages = new ArrayList<>();

    /**
     * 상품평 저장, 수정 이벤트 생성자입니다.
     *
     * @param imagePath 업로드한 이미지 경로
     */
    public ProductReviewSaveUpdateEvent(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * 상품평 저장, 수정 이벤트 생성자입니다.
     *
     * @param images 삭제된 수정 전 이미지파일들
     */
    public ProductReviewSaveUpdateEvent(List<CommonFile> images) {
        beforeImages.addAll(images);
    }
}
