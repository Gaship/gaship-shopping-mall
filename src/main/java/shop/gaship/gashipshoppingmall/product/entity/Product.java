package shop.gaship.gashipshoppingmall.product.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    Integer no;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_type_no")
    StatusCode deliveryType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_status_no")
    StatusCode salesStatus;

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
}
