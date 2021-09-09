package com.kkk.kspring2.multilang;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class MultiLangConfig {
    /**
     * 사용자 언어 환경을 셋팅해줘야 하기 때문에 bean을 추가
     * 즉, Locale.US로 설정했으면 messages_en_US.properties에 작성된 텍스트가 보여지게 됨
     * @return
     */
//    @Bean(name="localeResolver")
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
////        sessionLocaleResolver.setDefaultLocale(Locale.KOREAN);      // <---- 해당 값을 수정하여 언어 결정
//
//        return sessionLocaleResolver;
//    }

/*    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA); // 기본 한국어
        localeResolver.setCookieName("locale"); // 쿠키 이름 ; locale
        localeResolver.setCookieMaxAge(60 * 60); // 쿠키 살려둘 시간, -1로 하면 브라우져를 닫을 때 없어짐.
        localeResolver.setCookiePath("/"); // Path를 지정해 주면 해당하는 path와 그 하위 path에서만 참조
        return localeResolver;
    }*/
}
