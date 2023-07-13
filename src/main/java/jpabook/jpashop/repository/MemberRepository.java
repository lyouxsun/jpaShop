package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {
    /**
     * 회원 등록
     */
    @PersistenceContext
    private EntityManager em;   //엔티티매니저 생성
    public void save(Member member){
        em.persist(member);
    }

    /**
     * 회원 조회 (단건 조회)
     */
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    /**
     * 회원 목록 조회 (리스트 조회)
     */
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)//리스트 조회 시 이 메서드를 사용하여 JPQL작성 ("{JPQL}", {조회 타입})
                .getResultList();   //찾은 멤버들을 리스트로 만들어줌
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name=:name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    /*
    public Long save(Member member){
        em.persist(member);
        return member.getId();
        //member가 아니라 id를 반환하는 이유 : command와 query를 분리
        // (하기 위해서 리턴값을 거의 안 만들지만, id정도는 리턴하도록 한다.)
    }
     */
}
