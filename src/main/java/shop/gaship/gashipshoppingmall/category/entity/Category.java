package shop.gaship.gashipshoppingmall.category.entity;

import lombok.*;

import javax.persistence.*;
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
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Integer no;

    @Column
    private String name;

    @Column
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "upper_category_no")
    private Category upperCategory;

    @OneToMany(mappedBy = "upperCategory")
    private List<Category> lowerCategories;

    /**
     * methodName : updateCategory
     * author : 김보민
     * description : 카테고리 엔티티 내부 값 변경
     *
     * @param name update name
     * @return category
     */
    public Category updateCategory(String name) {
        this.name = name;
        return this;
    }
}
