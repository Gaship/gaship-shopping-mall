package shop.gaship.gashipshoppingmall.membergrade.entity;

import javax.persistence.*;
import lombok.*;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeRequestDto;
import shop.gaship.gashipshoppingmall.statuscode.entity.StatusCode;


/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.entity
 * fileName       : MemberGrade
 * author         : semi
 * date           : 2022/07/09
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/09        semi       최초 생성
 */
@NoArgsConstructor
@Getter
@Entity
@Table(name = "member_grades")
public class MemberGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_no")
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renewal_period_no")
    private StatusCode renewalPeriodStatusCode;

    @Column
    private String name;

    @Column(name = "accumulate_amount")
    private Long accumulateAmount;

    @Column(name = "is_default")
    private boolean isDefault;

    /**
     * Instantiates a new Member grade.
     *
     * @param renewalPeriod    the renewal period
     * @param name             the name
     * @param accumulateAmount the accumulateAmount
     */
    @Builder
    public MemberGrade(StatusCode renewalPeriod, String name,
                       Long accumulateAmount, Boolean isDefault) {
        this.renewalPeriodStatusCode = renewalPeriod;
        this.name = name;
        this.accumulateAmount = accumulateAmount;
        this.isDefault = isDefault;
    }

    /**.
     * methodName : createDefault
     * author : Semi Kim
     * description : 회원가입 및 기본 등급에 사용되는 회원 등급 생성시 사용되는 메서드
     *
     * @param renewalPeriod StatusCode
     * @param memberGradeRequestDto MemberGradeRequestDto
     * @return memberGrade MemberGrade
     */
    public static MemberGrade createDefault(StatusCode renewalPeriod,
                                            MemberGradeRequestDto memberGradeRequestDto) {
        return MemberGrade.builder()
                .renewalPeriod(renewalPeriod)
                .name(memberGradeRequestDto.getName())
                .accumulateAmount(memberGradeRequestDto.getAccumulateAmount())
                .isDefault(true)
                .build();
    }

    /**.
     * methodName : modifyDetails
     * author : Semi Kim
     * description : 회원등급의 세부 내용(회원등급명, 기준누적금액) 수정시 사용되는 메서드
     *
     * @param memberGradeRequestDto MemberGradeRequestDto
     */
    public void modifyDetails(MemberGradeRequestDto memberGradeRequestDto) {
        this.name = memberGradeRequestDto.getName();
        this.accumulateAmount = memberGradeRequestDto.getAccumulateAmount();
    }
}
