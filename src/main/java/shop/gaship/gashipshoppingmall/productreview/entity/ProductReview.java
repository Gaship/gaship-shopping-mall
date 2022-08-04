package shop.gaship.gashipshoppingmall.productreview.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.orderproduct.entity.OrderProduct;

/**
 * 상품평 엔티티입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product_reviews")
public class ProductReview {
    @Id
    private Integer orderProductNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_product_no")
    private OrderProduct orderProduct;

    private String title;

    private String content;

    private String imagePath;

    @NotNull(message = "별점은 필수입력사항입니다.")
    @Min(value = 1, message = "별점은 1이상 5이하여야 합니다.")
    @Max(value = 5, message = "별점은 1이상 5이하여야 합니다.")
    private Integer starScore;

    @NotNull
    private LocalDateTime registerDatetime;

    private LocalDateTime modifyDatetime;

    /**
     * 상품평 생성자입니다.
     *
     * @param orderProduct     주문상품
     * @param title            상품평 제목
     * @param content          상품평 내용
     * @param imagePath        상품평 이미지 경로
     * @param starScore        별점
     * @param registerDatetime 상품평 등록 일시
     */
    @Builder
    public ProductReview(OrderProduct orderProduct, String title, String content,
                         String imagePath, Integer starScore, LocalDateTime registerDatetime) {
        this.orderProduct = orderProduct;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
        this.starScore = starScore;
        this.registerDatetime = registerDatetime;
    }

    /**
     * 상품평 수정 메서드입니다.
     *
     * @param title     상품평 제목
     * @param content   상품평 내용
     * @param starScore 별점
     */
    public void updateProductReview(String title, String content, Integer starScore) {
        this.title = title;
        this.content = content;
        this.starScore = starScore;
        this.modifyDatetime = LocalDateTime.now();
    }

    /**
     * 상품평 이미지 경로 수정 메서드입니다.
     *
     * @param imagePath 이미지 경로
     */
    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
