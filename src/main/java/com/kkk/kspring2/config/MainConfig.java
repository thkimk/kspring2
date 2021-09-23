package com.kkk.kspring2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Configuration
@Order(value = 99999)
@AutoConfigureAfter(value = { FlywayAutoConfiguration.class })
public class MainConfig {

    /**
     * Bean 초기화 후 실행
     */
    @PostConstruct
    public void init() {
        log.info("## MainConfig [init] starts.. ");
    }


    @Bean
    public AtomicLong transId() {
        log.info("## MainConfig [transId] starts.. ");
        return new AtomicLong(1);
    }
}
