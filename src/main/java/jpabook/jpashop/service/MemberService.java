package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)     //조회에서 jpa 성능을 최적화 해줌
  //JPA의 모든 데이터 변경이나 로직들은 모두 트랜젝션 안에서 수행해야 한다 -> 그래야 지연로딩 이라던가..그런게 가능하다.
  //스프링이 제공하는 트랜젝셔널 어노테이션이 더 쓸 수 있는 기능이 많다. (추천!)
//@AllArgsConstructor       //모든 필드를 가지고 생성자를 만들어줌
@RequiredArgsConstructor        //final이 붙은 필드만 가지고 생성자 만들어줌
public class MemberService {

    //리포지토리를 인젝션 해주기

    private final MemberRepository memberRepository;

/* @AllArgsConstructor, @RequiredArgsConstructor 어노테이션이 있으면 스프링이 자동으로 만들어줌
    //@Autowired  생성자가 하나일 때에는 스프링이 자동으로 injection 해줌
    public MemberService (MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
*/

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);    // 중복 회원 검증 -> 이름이 중복되는 회원은 회원가입 불가능 (예외 터트리기)
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){ //중복 회원 존재
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * id로 회원 단건 조회
     */
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
