package shop.gaship.gashipshoppingmall.productimage.entity;

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
import shop.gaship.gashipshoppingmall.file.entity.File;
import shop.gaship.gashipshoppingmall.product.entity.Product;

/**
 * 상품 이미지 엔티티입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "product_images")
public class ProductImage {
    @EmbeddedId
    private Pk pk;

    @MapsId("fileNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_no")
    private File file;

    @MapsId("productNo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    private Product product;

    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @EqualsAndHashCode
    @Getter
    public static class Pk implements Serializable {
        private Integer fileNo;
        private Integer productNo;
    }
}
