package jpabook.jpashop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model){
        model.addAttribute("data", "hello!");
        return "hello"; //화면이름 (hello.html) -> 이 화면을 반환(view)하라
        //만약 랜더링 안하고 정적인 html을 넘기고 싶다면 static패키지 속에 html을 저장하면 된다.
    }
}
