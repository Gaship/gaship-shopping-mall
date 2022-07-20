package shop.gaship.gashipshoppingmall.productTag.entity;

import lombok.*;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 상품 태그 엔티티 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "product_tags")
public class ProductTag {
    @EmbeddedId
    private Pk pk;

    @MapsId(value = "productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    @Setter
    private Product product;

    @MapsId(value = "tagNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_no")
    private Tag tag;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @EqualsAndHashCode
    @Embeddable
    public static class Pk implements Serializable {
        private Integer productNo;
        private Integer tagNo;
    }
}
