package shop.gaship.gashipshoppingmall.product.entity;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * packageName    : shop.gaship.gashipshoppingmall.product.entity
 * fileName       : Product
 * author         : 김보민
 * date           : 2022-07-11
 * description    : 상품 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-11        김보민       최초 생성
 */
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

    @Column
    String name;

    @Column
    Long amount;

    @Column(name = "register_datetime")
    LocalDateTime registerDatetime;

    @Column
    String manufacturer;

    @Column(name = "manufacturer_country")
    String manufacturerCountry;

    @Column
    String seller;

    @Column
    String importer;

    @Column(name = "shipping_installation_cost")
    Long shippingInstallationCost;

    @Column(name = "quality_assurance_standard")
    String qualityAssuranceStandard;

    @Column
    String color;

    @Column(name = "stock_quantity")
    Integer stockQuantity;

    @Column(name = "image_link_1")
    String imageLink1;

    @Column(name = "image_link_2")
    String imageLink2;

    @Column(name = "image_link_3")
    String imageLink3;

    @Column(name = "image_link_4")
    String imageLink4;

    @Column(name = "image_link_5")
    String imageLink5;

    @Column
    String explanation;
}
