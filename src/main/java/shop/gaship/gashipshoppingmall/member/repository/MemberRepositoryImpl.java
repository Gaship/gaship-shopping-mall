package shop.gaship.gashipshoppingmall.member.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import shop.gaship.gashipshoppingmall.member.entity.Member;
import shop.gaship.gashipshoppingmall.member.entity.QMember;

/**
 * packageName    : shop.gaship.gashipshoppingmall.member.repository <br/>
 * fileName       : MemberRepositoryImpl <br/>
 * author         : 김민수 <br/>
 * date           : 2022/07/13 <br/>
 * description    : <br/>
 * ===========================================================  <br/>
 * DATE              AUTHOR             NOTE                    <br/>
 * -----------------------------------------------------------  <br/>
 * 2022/07/13           김민수               최초 생성                         <br/>
 */
public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {
    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public Member findByEmail(String email) {
        QMember member = QMember.member;

        return from(member)
            .where(member.email.eq(email))
            .select(member)
            .fetchOne();
    }
}
