package com.kkk.kspring2.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
@Slf4j
public class AsyncService {
    @Async
    public ListenableFuture<String> logger() throws InterruptedException {
        Thread.sleep(3000);
        log.info("서비스");
        return new AsyncResult<>("결과");
    }
}
