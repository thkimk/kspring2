package com.kkk.kspring2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/jsp")
public class JspController {
    @GetMapping("/upload")
    public ModelAndView upload() {
        ModelAndView mav = new ModelAndView("upload/index");
        return mav;
    }

}
