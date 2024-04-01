package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web1.dto.MemberDto;

@Log4j2
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public void login() {
        log.info("로그인 페이지 요청");
    }

    // @PostMapping("/login")
    // public void loginPost(String email, String name) {
    // log.info("로그인 정보 가져오기");
    // log.info("email : {}", email);
    // log.info("name : {}", name);
    // }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("mDto") MemberDto mDto, @ModelAttribute int page, Model model) {
        log.info("로그인 정보 가져오기");
        log.info("email : {}", mDto.getEmail());
        log.info("name : {}", mDto.getName());
        log.info("page : {}", page);

        // model.addAttribute("page", page); == @ModelAttribute("page")

        // 기본적으로 forward 방식으로 페이지 이동
        return "/member/info";
    }

    // 데이터 보내기
    // request.getAttribute("이름", 값) == Model

}
