package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 페이지 조회
     */
    @GetMapping("members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());
        return"members/createMemberForm";
    }
    /**
     * 회원가입 정보 등록
     */
    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult bindingResult){     //스프링한테 MemberForm에 있는 validation기능을 쓰겠다고 알려주는 어노테이션
        if(bindingResult.hasErrors()){
            return("members/createMemberForm");
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());
        Member member = new Member();
        member.setAddress(address);
        member.setName(memberForm.getName());

        memberService.join(member);
        return "redirect:/";
    }

    /**
     * 회원 목록 조회
     */
    @GetMapping("/members/list")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
    //API를 만들 때에는 절.대.로 entity를 외부로 반환하면 안된다!!!!!
    //->  dto 사용하기
}
