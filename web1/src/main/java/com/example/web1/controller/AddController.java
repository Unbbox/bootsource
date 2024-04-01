package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.web1.dto.AddDto;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Log4j2
@Controller
@RequestMapping("/calc")
public class AddController {

    @GetMapping("/add")
    public void addGet() {
        log.info("/calc/add 요청");
    }

    // 사용자 입력 값 가져오기
    // 1. HttpServletRequest req
    // 2. 파라미터 이용(폼 이름과 변수명 일치)
    // 3. DTO 이용

    // @PostMapping("/add")
    // public void addPost(HttpServletRequest req) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", req.getParameter("num1"));
    // log.info("num2 {}", req.getParameter("num2"));
    // }

    // @PostMapping("/add")
    // public void addPost(@RequestParam(value = "num1", defaultValue = "0") int
    // num1,
    // @RequestParam(value = "num2", defaultValue = "0") int num2) {
    // log.info("/calc/add post 요청");
    // log.info("num1 {}", num1);
    // log.info("num2 {}", num2);
    // }

    @PostMapping("/add")
    public void addPost(AddDto dto, Model model) {
        log.info("/calc/add post 요청");
        log.info("num1 {}", dto.getNum1());
        log.info("num1 {}", dto.getNum2());

        // dto.setResult(dto.getNum1() + dto.getNum2());
        model.addAttribute("result", dto.getNum1() + dto.getNum2());
    }

    // 데이터 보내기
    // request.getAttribute("이름", 값) == Model

    @GetMapping("/rules")
    public void rule() {
        log.info("사칙연산 페이지 요청");
    }

    @PostMapping("/rules")
    public String ruleResult(AddDto addDto, @ModelAttribute("op") String op, Model model) {
        log.info("/calc/rules post 요청");
        log.info("num1 : {}", addDto.getNum1());
        log.info("op : {}", op);
        log.info("num2 : {}", addDto.getNum2());

        log.info("/calc/rules post 요청");
        int result = 0;

        switch (op) {
            case "+":
                result = addDto.getNum1() + addDto.getNum2();
                break;
            case "-":
                result = addDto.getNum1() - addDto.getNum2();
                break;
            case "*":
                result = addDto.getNum1() * addDto.getNum2();
                break;
            case "/":
                result = addDto.getNum1() / addDto.getNum2();
                break;
            default:
                break;
        }

        // model.addAttribute("result", result);
        addDto.setResult(result);

        return "/calc/result";
    }
}
