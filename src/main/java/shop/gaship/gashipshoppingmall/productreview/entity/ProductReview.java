package shop.gaship.gashipshoppingmall.productreview.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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

    private Integer starScore;

    private LocalDateTime registerDatetime;

    private LocalDateTime modifyDatetime;

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

    public void updateProductReview(String title, String content, Integer starScore) {
        this.title = title;
        this.content = content;
        this.starScore = starScore;
        this.modifyDatetime = LocalDateTime.now();
    }

    public void updateImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
