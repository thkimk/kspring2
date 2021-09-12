package com.kkk.kspring2.multilang;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Controller
@RequestMapping("/multilang")
@Slf4j
public class MultiLangController {
    @Autowired
    LocaleResolver localeResolver;

    @Autowired
    MessageSource messageSource;


    @GetMapping("/test")
    public String test() {
        log.info("## test..");
        System.out.println("## jpa1() : begins.."+ messageSource.getMessage("product", null, Locale.US));
        return "multilang/index";
    }

    @GetMapping("/test2")
    public Object test2(Locale locale, HttpServletRequest request, Model model) {
        log.info("## test2 : "+ locale);
        log.info("## test2 : "+ Locale.getDefault());

        log.info("Session locale : {}", localeResolver.resolveLocale(request));
        System.out.println("## jpa1() : begins.."+ messageSource.getMessage("product", null, locale));
        System.out.println("## jpa1() : begins.."+ messageSource.getMessage("product", null, localeResolver.resolveLocale(request)));

        return "multilang";
    }

    @RequestMapping(value = "/i18n.do", method = RequestMethod.GET)
    public String i18n(Locale locale, HttpServletRequest request, Model model) {
        // RequestMapingHandler로 부터 받은 Locale 객체를 출력해 봅니다.
        model.addAttribute("clientLocale", locale);

        // localeResolver 로부터 Locale 을 출력해 봅니다.
        model.addAttribute("sessionLocale", localeResolver.resolveLocale(request));

        // JSP 페이지에서 EL 을 사용해서 arguments 를 넣을 수 있도록 값을 보낸다.
        model.addAttribute("siteCount", messageSource.getMessage("msg.first", null, locale));

        return "multilang/i18n";
    }

    @ResponseBody
    @RequestMapping(path = "/locale")
    public String locale(Locale locale) {
        String lang = locale.toString();
        String home = messageSource.getMessage("product", null, locale);
        System.out.println("## locale: "+ locale);System.out.println("## "+ lang+ ", "+ home);

        return lang + " : " + home;
    }
}
