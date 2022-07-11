package shop.gaship.gashipshoppingmall.membergrade.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import shop.gaship.gashipshoppingmall.membergrade.dto.MemberGradeDto;

/**
 * packageName    : shop.gaship.gashipshoppingmall.membergrade.repository
 * fileName       : MemberGradeRepositoryCustom
 * author         : Semi Kim
 * date           : 2022/07/11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/07/11        Semi Kim       최초 생성
 */
public interface MemberGradeRepositoryCustom {
    Optional<MemberGradeDto> getMemberGradeBy(Integer memberGradeNo);

    List<MemberGradeDto> getMemberGrades(Pageable page);
}
