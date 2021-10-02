package com.kkk.kspring2.async;

import com.kkk.kspring2.thymeleaf.ThymeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/async")
@Slf4j
public class AsyncController {
    private AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @RequestMapping("/test1")
    public String test1(Model model) throws InterruptedException {
        asyncService.logger().addCallback((result) -> {
            log.info(result);
        }, (e) -> {
            log.error(e.getMessage(), e);
        });
        log.info("컨트롤러");
        return "ok";
    }

}
