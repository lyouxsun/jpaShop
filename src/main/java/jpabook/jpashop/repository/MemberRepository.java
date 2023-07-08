package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    EntityManager em;       //엔티티매니저 생성
    public Long save(Member member){
        em.persist(member);
        return member.getId();
        //member가 아니라 id를 반환하는 이유 : command와 query를 분리
        // (하기 위해서 리턴값을 거의 안 만들지만, id정도는 리턴하도록 한다.)
    }

    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
