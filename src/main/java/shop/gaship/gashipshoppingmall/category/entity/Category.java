package shop.gaship.gashipshoppingmall.category.entity;

import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : shop.gaship.gashipshoppingmall.category.entity
 * fileName       : Category
 * author         : 김보민
 * date           : 2022-07-09
 * description    : 카테고리 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022-07-08        김보민       최초 생성
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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
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

    public void insertLowerCategory(Category lowerCategory) {
        lowerCategory.updateUpperCategory(this);

        lowerCategories.add(lowerCategory);
    }

    /**
     * methodName : updateUpperCategory
     * author : 김보민
     * description : 카테고리 엔티티 상위 카테고리 변경
     *
     * @param category category
     */
    public void updateUpperCategory(Category category) {
        this.upperCategory = category;
    }

    /**
     * methodName : updateCategoryName
     * author : 김보민
     * description : 카테고리 엔티티 이름 변경
     *
     * @param name update name
     */
    public void updateCategoryName(String name) {
        this.name = name;
    }
}
