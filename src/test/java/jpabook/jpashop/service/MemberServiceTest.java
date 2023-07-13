package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)    //  스프링이랑 integration해서 테스트 진행하기 위한 어노테이션 2개
@SpringBootTest
@Transactional  //데이터가 롤백됨 (DB에 저장되지 않음), 테스트 케이스에서만 가능
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    //@Rollback(false) : DB에 insert 쿼리가 날라감 -> DB에 저장됨
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("이영선");
        
        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); //DB에 insert쿼리가 날라가지만, 트랜잭션은 롤백을 해서 DB에 저장되지는 않음
        assertEquals(member, memberService.findOne(savedId));
        //assertEquals(savedId, member.getId());     //얘도 같은 역할인듯
    }
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("이영선1");
        Member member2 = new Member();
        member2.setName("이영선1");
        
        //when
        memberService.join(member1);
        memberService.join(member2);
/*    @Test(expected = IllegalStateException.class) 이걸 쓰면 아래 코드는 안써도됨

        try{
            memberService.join(member2);        //예외가 발생해야 한다. -> 예외가 발생하면 예외가 터지니까 fail을 만나지 못함
        } catch(IllegalStateException e){
            return;
        }
*/

        //then
        fail("예외가 발생해야 한다.");        //얘를 만난것 자체가 에러가 떠야한다. 저 메세지로
    }
}