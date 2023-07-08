/*
package jpabook.jpashop;


import jpabook.jpashop.Member;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)        //junit에게 스프링과 관련된 테스트를 하겠다고 알려주는 어노테이ㄴ
@SpringBootTest
@Rollback(false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    //멤버 리포지토리가 잘 작동하는지 확인하는 test이므로 injection을 받는다. (필드주입, DI)

    @Test
    @Transactional
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("이영선");

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        //assertThat(findMember).isEqualTo(member);       //같은 트랜잭션 안에서 저장하고 조회하면 영속성 컨텍스트가 똑같다
        //-> 같은 영속성 컨텍스트에서는 id값(식별자)이 같으면 같은 entity로 식별된다.
    }
}
 */
package jpabook.jpashop;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
//import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {
        Member member = new Member();
        member.setUsername("memberA");
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    }
}