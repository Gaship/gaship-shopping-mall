package shop.gaship.gashipshoppingmall.gradehistory.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.gaship.gashipshoppingmall.gradehistory.dto.request.GradeHistoryAddRequestDto;
import shop.gaship.gashipshoppingmall.member.entity.Member;

/**
 * 등급이력 Entity class.
 *
 * @author : 김세미
 * @since 1.0
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "grade_histories")
public class GradeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_history_no", nullable = false)
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "grade_at", nullable = false)
    private LocalDate at;

    @Column(name = "grade_total_amount", nullable = false)
    private Long totalAmount;

    @NotNull(message = "gradeName 은 필수값 입니다.")
    private String gradeName;

    /**
     * Instantiates a new Grade history.
     *
     * @param member  승급한 등급이력 주인 멤버 (Member)
     * @param request 등급이력 등록 요청 Dto (GradeHistoryAddRequestDto)
     */
    @Builder
    public GradeHistory(Member member, GradeHistoryAddRequestDto request) {
        this.member = member;
        this.at = LocalDate.now();
        this.totalAmount = request.getTotalAmount();
        this.gradeName = request.getGradeName();
    }
}
