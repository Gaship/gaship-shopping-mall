package shop.gaship.gashipshoppingmall.category.entity;

import java.util.ArrayList;
import java.util.List;
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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 카테고리 엔티티입니다.
 *
 * @author : 김보민
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no", nullable = false)
    private Integer no;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 1, message = "카테고리는 총 3종류입니다. (대분류, 중분류, 소분류)")
    @Max(value = 3, message = "카테고리는 총 3종류입니다. (대분류, 중분류, 소분류)")
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "upper_category_no")
    private Category upperCategory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "upper_category_no", referencedColumnName = "category_no")
    private final List<Category> lowerCategories = new ArrayList<>();
    
    public Category(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    /**
     * 하위 카테고리를 생성하는 메서드 입니다.
     *
     * @param lowerCategory 추가할 하위 카테고리
     * @author 김보민
     */
    public void insertLowerCategory(Category lowerCategory) {
        lowerCategory.updateUpperCategory(this);

        lowerCategories.add(lowerCategory);
    }

    /**
     * 카테고리의 상위 카테고리를 업데이트하는 메서드 입니다.
     *
     * @param category 업데이트할 상위 카테고리
     * @author 김보민
     */
    public void updateUpperCategory(Category category) {
        this.upperCategory = category;
    }

    /**
     * 카테고리의 이름을 업데이트하는 메서드 입니다.
     *
     * @param name 업데이트할 이름
     * @author 김보민
     */
    public void updateCategoryName(String name) {
        this.name = name;
    }
}
