package com.kkk.kspring2.multilang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/multilangrest")
@Slf4j
public class MultiLangRestController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test")
    public String test() {
        log.info("## test..");
        System.out.println("## jpa1() : begins.."+ messageSource.getMessage("product", null, Locale.getDefault()));
        return "multilang";
    }
}
