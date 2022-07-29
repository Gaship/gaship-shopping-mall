package shop.gaship.gashipshoppingmall.productreview.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.entity.BaseEntity;
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
public class ProductReview extends BaseEntity {
    @Id
    @Column(name = "order_product_no")
    private Integer orderProductNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "order_product_no")
    private OrderProduct orderProduct;

    private String title;

    private String content;

    private String imagePath;

    private Integer starScore;
}
