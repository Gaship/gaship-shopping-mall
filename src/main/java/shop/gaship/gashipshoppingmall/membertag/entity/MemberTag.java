package shop.gaship.gashipshoppingmall.membertag.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.tag.entity.Tag;

import javax.persistence.*;

/**
 * MemberTag Entity class
 *
 * @author 최정우
 * @since 1.0
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member_tags")
public class MemberTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberTagNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_no")
    private Tag tag;
}
