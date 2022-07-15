package shop.gaship.gashipshoppingmall.membergrade.entity;

import javax.persistence.*;
import lombok.*;
import shop.gaship.gashipshoppingmall.membergrade.dto.request.MemberGradeModifyRequestDto;
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
    @Column(name = "member_grade_no", nullable = false)
    private Integer no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renewal_period_no", nullable = false)
    private StatusCode renewalPeriodStatusCode;

    @Column(nullable = false)
    private String name;

    @Column(name = "accumulate_amount", unique = true, nullable = false)
    private Long accumulateAmount;

    @Column(name = "is_default", nullable = false)
    private boolean isDefault;

    /**
     * Instantiates a new Member grade.
     *
     * @param renewalPeriod         the renewalPeriod
     * @param memberGradeRequestDto the memberGradeRequestDto
     * @param isDefault             the isDefault
     */
    @Builder
    private MemberGrade(StatusCode renewalPeriod,
                        MemberGradeRequestDto memberGradeRequestDto,
                        Boolean isDefault) {
        this.renewalPeriodStatusCode = renewalPeriod;
        this.name = memberGradeRequestDto.getName();
        this.accumulateAmount = memberGradeRequestDto.getAccumulateAmount();
        this.isDefault = isDefault;
    }

    /**.
     * methodName : createDefault
     * author : Semi Kim
     * description : 회원가입 및 기본 등급에 사용되는 회원 등급 생성시 사용되는 메서드
     *
     * @param renewalPeriod 회원등급 갱신기간 관련 StatusCode
     * @param memberGradeRequestDto MemberGradeRequestDto
     * @return memberGrade MemberGrade
     */
    public static MemberGrade createDefault(StatusCode renewalPeriod,
                                            MemberGradeRequestDto memberGradeRequestDto) {
        return new MemberGrade(renewalPeriod, memberGradeRequestDto, true);
    }

    /**.
     * methodName : create
     * author : Semi Kim
     * description : 기본 등급 이외의 등급 생성시 사용되는 메서드
     *
     * @param renewalPeriod 회원등급 갱신기간 관련 StatusCode
     * @param memberGradeRequestDto MemberGradeRequestDto
     * @return member grade
     */
    public static MemberGrade create(StatusCode renewalPeriod,
                                     MemberGradeRequestDto memberGradeRequestDto) {
        return new MemberGrade(renewalPeriod, memberGradeRequestDto, false);
    }

    /**.
     * methodName : modifyDetails
     * author : Semi Kim
     * description : 회원등급의 세부 내용(회원등급명, 기준누적금액) 수정시 사용되는 메서드
     *
     * @param modifyRequestDto MemberGradeModifyRequestDto
     */
    public void modifyDetails(MemberGradeModifyRequestDto modifyRequestDto) {
        this.name = modifyRequestDto.getName();
        this.accumulateAmount = modifyRequestDto.getAccumulateAmount();
    }
}
