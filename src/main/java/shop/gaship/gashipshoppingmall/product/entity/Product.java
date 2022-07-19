package shop.gaship.gashipshoppingmall.product.entity;

import java.awt.image.Kernel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    Integer no;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "category_no")
    Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "delivery_type_no")
    StatusCode deliveryType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
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

    @NotNull
    String code;

    @Builder
    public Product(Category category, StatusCode deliveryType, String name,
                   Long amount, LocalDateTime registerDatetime, String manufacturer,
                   String manufacturerCountry, String seller, String importer,
                   Long shippingInstallationCost, String qualityAssuranceStandard, String color,
                   Integer stockQuantity, String explanation, String code) {
        this.category = category;
        this.deliveryType = deliveryType;
        this.name = name;
        this.amount = amount;
        this.registerDatetime = registerDatetime;
        this.manufacturer = manufacturer;
        this.manufacturerCountry = manufacturerCountry;
        this.seller = seller;
        this.importer = importer;
        this.shippingInstallationCost = shippingInstallationCost;
        this.qualityAssuranceStandard = qualityAssuranceStandard;
        this.color = color;
        this.stockQuantity = stockQuantity;
        this.explanation = explanation;
        this.code = code;
    }

    public static Product create(Category category, StatusCode deliveryType, ProductCreateRequestDto createRequest) {
        return Product.builder()
                .category(category)
                .deliveryType(deliveryType)
                .amount(createRequest.getAmount())
                .registerDatetime(LocalDateTime.now())
                .manufacturer(createRequest.getManufacturer())
                .manufacturerCountry(createRequest.getManufacturerCountry())
                .seller(createRequest.getSeller())
                .importer(createRequest.getImporter())
                .shippingInstallationCost(createRequest.getShippingInstallationCost())
                .qualityAssuranceStandard(createRequest.getQualityAssuranceStandard())
                .color(createRequest.getColor())
                .stockQuantity(createRequest.getStockQuantity())
                .explanation(createRequest.getExplanation())
                .build();
    }

    public void updateSalesStatus(StatusCode salesStatus) {
        this.salesStatus = salesStatus;
    }

    public void updateImageLinks(List<String> imageLinks) {
        String[] updatingImageLinks = new String[5];
        IntStream.range(0, imageLinks.size()).forEach(i -> updatingImageLinks[i] = imageLinks.get(i));

        this.imageLink1 = updatingImageLinks[0];
        this.imageLink2 = updatingImageLinks[1];
        this.imageLink3 = updatingImageLinks[2];
        this.imageLink4 = updatingImageLinks[3];
        this.imageLink5 = updatingImageLinks[4];
    }
}
