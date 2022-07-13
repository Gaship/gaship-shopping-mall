package shop.gaship.gashipshoppingmall.member.repository;

import org.springframework.data.repository.NoRepositoryBean;
import shop.gaship.gashipshoppingmall.member.entity.Member;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.repository <br/>
 * fileName       : CustomMemberRepository <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
@NoRepositoryBean
public interface MemberRepositoryCustom {
    Member findByEmail(String email);
}
