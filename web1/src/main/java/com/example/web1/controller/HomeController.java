package com.example.web1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class HomeController {

    // @RequestMapping(value = "/", method = RequestMethod.GET)
    @GetMapping("/")
    public String home() {
        // c.e.web1.controller.HomeController : home 요청
        log.info("home 요청"); // sout과 같은 의미

        // templates 아래 경로부터 확장자 빼고 파일명 작성
        return "index";
    }
}
