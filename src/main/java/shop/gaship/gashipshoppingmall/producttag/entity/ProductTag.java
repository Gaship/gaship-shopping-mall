package shop.gaship.gashipshoppingmall.producttag.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.product.entity.Product;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

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
@Table(name = "product_tags")
public class ProductTag {
    @EmbeddedId
    private Pk pk;

    @MapsId(value = "productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @MapsId(value = "tagNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_no")
    private Tag tag;

    /**
     * 상품 태그 엔티티 식별자 클래스 입니다.
     *
     * @author : 김보민
     * @since 1.0
     */
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
