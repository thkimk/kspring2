package com.kkk.kspring2.multilang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping("/multilang")
@Slf4j
public class MultiLangController {
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/test")
    public String test() {
        log.info("## test..");
//        System.out.println("## jpa1() : begins.."+ messageSource.getMessage("product", null, Locale.US));
        return "multilang";
    }

    @GetMapping("/test2")
    public Object test2() {
        log.info("## test..");
//        System.out.println("## jpa1() : begins.."+ messageSource.getMessage("product", null, Locale.US));
        return "multilang";
    }

}
