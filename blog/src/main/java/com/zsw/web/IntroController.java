package com.zsw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IntroController {
    @GetMapping("/")
    public String intro(){
        return "intro";

    }
}




