package shop.gaship.gashipshoppingmall.product.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_type_no")
    StatusCode deliveryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_status_no")
    StatusCode salesStatus;

    String name;

    Long amount;

    LocalDateTime registerDatetime;

    String manufacturer;

    String manufacturerCountry;

    String seller;

    String importer;

    @Column(name = "shipping_installation_cost")
    Long shippingInstallationCost;

    String qualityAssuranceStandard;

    String color;

    Integer stockQuantity;

    String imageLink1;

    String imageLink2;

    String imageLink3;

    String imageLink4;

    @Column
    String imageLink5;

    String explanation;
}
