package shop.gaship.gashipshoppingmall.product.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import shop.gaship.gashipshoppingmall.category.entity.Category;
import shop.gaship.gashipshoppingmall.commonfile.entity.CommonFile;
import shop.gaship.gashipshoppingmall.product.dto.request.ProductRequestDto;
import shop.gaship.gashipshoppingmall.producttag.entity.ProductTag;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;

/**
 * 상품 엔티티 클래스 입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private Integer no;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_no")
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "delivery_type_no")
    private StatusCode deliveryType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sales_status_no")
    private StatusCode salesStatus;

    @OneToMany(mappedBy = "product")
    private List<ProductTag> productTags = new ArrayList<>();

    @Length(max = 100, message = "상품 이름은 100자 이하여야 합니다.")
    @NotNull
    private String name;

    @Min(value = 0, message = "금액은 0보다 커야 합니다.")
    @NotNull
    Long amount;

    @NotNull
    private LocalDateTime registerDatetime;

    @Length(max = 20, message = "제조사는 20자 이하여야 합니다.")
    @NotNull
    private String manufacturer;

    @Length(max = 20, message = "제조국은 20자 이하여야 합니다.")
    @NotNull
    private String manufacturerCountry;

    @Length(max = 20, message = "판매자는 20자 이하여야 합니다.")
    @NotNull
    private String seller;

    @Length(max = 20, message = "수입자는 20자 이하여야 합니다.")
    @NotNull
    private String importer;

    @Min(value = 0, message = "금액은 0 이상이어야 합니다.")
    @NotNull
    private Long shippingInstallationCost;

    @Length(max = 255, message = "품질보증기준은 255자 이하여야 합니다.")
    @NotNull
    private String qualityAssuranceStandard;

    @NotNull
    private String color;

    @Min(value = 0, message = "재고량은 0 이상이여야 합니다.")
    @NotNull
    private Integer stockQuantity;

    @NotNull
    private String explanation;

    @Length(max = 100, message = "제품코드는 100자 이하여야 합니다.")
    @NotNull
    @Column(name = "product_code", unique = true)
    private String code;

    @Transient
    private static final String SERVICE = "product";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_no", referencedColumnName = "product_no")
    @Where(clause = "service = product")
    private final List<CommonFile> productImages = new ArrayList<>();

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
    public static Product create(Category category, StatusCode deliveryType,
                                 ProductRequestDto createRequest) {
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
    public void updateProduct(Category category, StatusCode deliveryType,
                              ProductRequestDto modifyRequest) {
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

    public void addProductImage(CommonFile commonFile){
        commonFile.updateCommonFile(no, SERVICE);
        productImages.add(commonFile);
    }
}
