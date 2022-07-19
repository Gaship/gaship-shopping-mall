package shop.gaship.gashipshoppingmall.product.entity;

import lombok.*;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    Integer no;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_no")
    Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "delivery_type_no")
    StatusCode deliveryType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sales_status_no")
    StatusCode salesStatus;

    @NotNull
    @OneToMany(mappedBy = "product")
    List<ProductTag> productTags = new ArrayList<>();
    @NotNull
    String name;
    @NotNull
    Long amount;
    @NotNull
    LocalDateTime registerDatetime;
    @NotNull
    String manufacturer;
    @NotNull
    String manufacturerCountry;
    @NotNull
    String seller;
    @NotNull
    String importer;
    @NotNull
    Long shippingInstallationCost;
    @NotNull
    String qualityAssuranceStandard;
    @NotNull
    String color;
    @NotNull
    Integer stockQuantity;
    @NotNull
    String imageLink1;
    String imageLink2;
    String imageLink3;
    String imageLink4;
    String imageLink5;
    @NotNull
    String explanation;
    @NotNull
    String productCode;

    public void add(ProductTag productTag) {
        productTag.setProduct(this);
        productTags.add(productTag);
    }

}
