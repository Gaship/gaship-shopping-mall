package shop.gaship.gashipshoppingmall.category.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 카테고리 엔티티
 *
 * @author : 김보민
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no", nullable = false)
    private Integer no;

    @NotNull
    private String name;

    @NotNull
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "upper_category_no")
    private Category upperCategory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "upper_category_no", referencedColumnName = "category_no")
    private List<Category> lowerCategories = new ArrayList<>();
    
    public Category(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    /**
     *
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
     *
     * 카테고리의 상위 카테고리를 업데이트하는 메서드 입니다.
     *
     * @param category 업데이트할 상위 카테고리
     * @author 김보민
     */
    public void updateUpperCategory(Category category) {
        this.upperCategory = category;
    }

    /**
     *
     * 카테고리의 이름을 업데이트하는 메서드 입니다.
     *
     * @param name 업데이트할 이름
     * @author 김보민
     */
    public void updateCategoryName(String name) {
        this.name = name;
    }
}
