package com.zsw.web.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class admiController {
    @GetMapping("/admi")
    public String admi(){
        return "manage/admi";

    }

}
