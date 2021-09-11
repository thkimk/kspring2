package com.kkk.kspring2.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thyme")
public class ThymeController {

    /**
     * test url : http://localhost:8091/thyme/test
     * local : /resources/templates/test1.html
     */
    @RequestMapping("/test1")
    public String test1(Model model) {
        ThymeData user = new ThymeData("kkaok", "테스트", "web") ;
        model.addAttribute("user", user);

        System.out.printf("## getUser");
        return "thymeleaf/test1";
    }
}
