package shop.gaship.gashipshoppingmall.productreview.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
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

    @Size(max = 100, message = "상품평 제목은 100자를 넘을 수 없습니다.")
    private String title;

    private String content;

    @NotNull(message = "별점은 필수입력사항입니다.")
    @Min(value = 1, message = "별점은 1이상 5이하여야 합니다.")
    @Max(value = 5, message = "별점은 1이상 5이하여야 합니다.")
    private Integer starScore;

    @NotNull
    private LocalDateTime registerDatetime;

    private LocalDateTime modifyDatetime;

    @Transient
    private static final String SERVICE = "productReview";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_no", referencedColumnName = "order_product_no")
    @Where(clause = "service = 'productReview'")
    private final List<CommonFile> reviewImages = new ArrayList<>();

    /**
     * 상품평 생성자입니다.
     *
     * @param orderProduct     주문상품
     * @param title            상품평 제목
     * @param content          상품평 내용
     * @param starScore        별점
     * @param registerDatetime 상품평 등록 일시
     */
    @Builder
    public ProductReview(OrderProduct orderProduct, String title, String content,
                         Integer starScore, LocalDateTime registerDatetime) {
        this.orderProduct = orderProduct;
        this.title = title;
        this.content = content;
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

    public void addProductReviewImage(CommonFile commonFile){
        commonFile.updateCommonFile(orderProductNo, SERVICE);
        reviewImages.add(commonFile);
    }
}
