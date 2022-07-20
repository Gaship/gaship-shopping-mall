package shop.gaship.gashipshoppingmall.tag.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.tag.dto.TagRequestDto;

import javax.persistence.*;

@Entity
@Table(name = "tags")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_no")
    private Integer tagNo;

    private String title;

    public void modifyEntity(TagRequestDto tagRequestDto) {
        this.title = tagRequestDto.getTitle();
    }
}
