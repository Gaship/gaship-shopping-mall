package shop.gaship.gashipshoppingmall.product.event;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.file.dto.FileRequestDto;

/**
 * 상품 생성 및 수정 이벤트 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
public class ProductSaveUpdateEvent {
    private final List<FileRequestDto> imageLinks;
    private final List<CommonFile> beforeImages = new ArrayList<>();

    /**
     * 상품 생성, 수정 이벤트 생성자입니다.
     *
     * @param imageLinks   업로드할 이미지 링크
     */
    public ProductSaveUpdateEvent(List<FileRequestDto> imageLinks) {
        this.imageLinks = imageLinks;
    }

    /**
     * 삭제된 수정 전 이미지파일들을 업데이트하는 메서드입니다.
     * 상품 수정이 정상적으로 커밋될 시 로컬에 업로드되었던 이전 이미지파일들을 삭제하기위함입니다.
     *
     * @param images 삭제한 이미지파일들
     */
    public void updateBeforeImages(List<CommonFile> images) {
        beforeImages.addAll(images);
    }
}
