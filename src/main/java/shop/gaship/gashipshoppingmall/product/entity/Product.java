package shop.gaship.gashipshoppingmall.product.entity;

import java.awt.image.Kernel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductCreateRequestDto;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductModifyRequestDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;
import shop.gaship.gashipshoppingmall.productTag.entity.ProductTag;

/**
 * 상품 엔티티 클래스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
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
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    List<ProductTag> productTags = new ArrayList<>();

    @Length(max = 100, message = "상품 이름은 100자 이하여야 합니다.")
    @NotNull
    String name;

    @Min(value = 0, message = "금액은 0보다 커야 합니다.")
    @NotNull
    Long amount;

    @NotNull
    LocalDateTime registerDatetime;

    @Length(max = 20, message = "제조사는 20자 이하여야 합니다.")
    @NotNull
    String manufacturer;

    @Length(max = 20, message = "제조국은 20자 이하여야 합니다.")
    @NotNull
    String manufacturerCountry;

    @Length(max = 20, message = "판매자는 20자 이하여야 합니다.")
    @NotNull
    String seller;

    @Length(max = 20, message = "수입자는 20자 이하여야 합니다.")
    @NotNull
    String importer;

    @Min(value = 0, message = "금액은 0 이상이어야 합니다.")
    @NotNull
    Long shippingInstallationCost;

    @Length(max = 255, message = "품질보증기준은 255자 이하여야 합니다.")
    @NotNull
    String qualityAssuranceStandard;

    @NotNull
    String color;

    @Min(value = 0, message = "재고량은 0 이상이여야 합니다.")
    @NotNull
    Integer stockQuantity;

    @Length(max = 255, message = "이미지링크는 255자 이하여야 합니다.")
    @NotNull
    String imageLink1;

    @Length(max = 255, message = "이미지링크는 255자 이하여야 합니다.")
    String imageLink2;

    @Length(max = 255, message = "이미지링크는 255자 이하여야 합니다.")
    String imageLink3;

    @Length(max = 255, message = "이미지링크는 255자 이하여야 합니다.")
    String imageLink4;

    @Length(max = 255, message = "이미지링크는 255자 이하여야 합니다.")
    String imageLink5;

    @NotNull
    String explanation;

    @Length(max = 100, message = "제품코드는 100자 이하여야 합니다.")
    @NotNull
    @Column(name = "product_code", unique = true)
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

    /**
     * 상품 생성 메서드입니다.
     *
     * @param category 상품의 카테고리
     * @param deliveryType 상품의 배송형태
     * @param createRequest 상품 생성 요청 dto
     * @return product 생성 상품
     * @author 김보민
     */
    public static Product create(Category category, StatusCode deliveryType, ProductCreateRequestDto createRequest) {
        return Product.builder()
                .category(category)
                .deliveryType(deliveryType)
                .name(createRequest.getName())
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
                .code(createRequest.getCode())
                .build();
    }

    /**
     * 상품 수정 메서드입니다.
     *
     * @param category 상품의 카테고리
     * @param deliveryType 상품의 배송형태
     * @param modifyRequest 상품 수정 요청 dto
     * @author 김보민
     */
    public void updateProduct(Category category, StatusCode deliveryType, ProductModifyRequestDto modifyRequest) {
        this.category = category;
        this.deliveryType = deliveryType;
        this.name = modifyRequest.getName();
        this.amount = modifyRequest.getAmount();
        this.manufacturer = modifyRequest.getManufacturer();
        this.manufacturerCountry = modifyRequest.getManufacturerCountry();
        this.seller = modifyRequest.getSeller();
        this.importer = modifyRequest.getImporter();
        this.shippingInstallationCost = modifyRequest.getShippingInstallationCost();
        this.qualityAssuranceStandard = modifyRequest.getQualityAssuranceStandard();
        this.color = modifyRequest.getColor();
        this.stockQuantity = modifyRequest.getStockQuantity();
        this.explanation = modifyRequest.getExplanation();
        this.code = modifyRequest.getCode();
    }

    /**
     * 상품 판매상태 수정 메서드입니다.
     *
     * @param salesStatus 수정할 판매상태
     * @author 김보민
     */
    public void updateSalesStatus(StatusCode salesStatus) {
        this.salesStatus = salesStatus;
    }

    /**
     * 서버에 업로드한 상품의 이미지들을 엔티티에 업데이트하는 메서드입니다.
     *
     * @param imageLinks 서버에 업로드한 이미지 이름 리스트
     * @author 김보민
     */
    public void updateImageLinks(List<String> imageLinks) {
        String[] updatingImageLinks = new String[5];
        IntStream.range(0, imageLinks.size()).forEach(i -> updatingImageLinks[i] = imageLinks.get(i));

        this.imageLink1 = updatingImageLinks[0];
        this.imageLink2 = updatingImageLinks[1];
        this.imageLink3 = updatingImageLinks[2];
        this.imageLink4 = updatingImageLinks[3];
        this.imageLink5 = updatingImageLinks[4];
    }

    /**
     * 상품의 이미지 링크들을 리스트형태로 반환하는 메서드입니다.
     *
     * @return imageLinks 이미지 링크 리스트
     */
    public List<String> getImageLinkList() {
        List<String> imageLinks = new ArrayList<>();

        imageLinks.add(this.imageLink1);
        imageLinks.add(this.imageLink2);
        imageLinks.add(this.imageLink3);
        imageLinks.add(this.imageLink4);
        imageLinks.add(this.imageLink5);
        imageLinks.removeAll(Collections.singletonList(null));

        return imageLinks;
    }
}
