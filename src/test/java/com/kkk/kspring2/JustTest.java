package com.kkk.kspring2;

import java.util.*;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class JustTest {
    public static void main(String[] args) {
        String text = "STRING";
        Mono<String> mono = Mono.just(text); //단지!
        mono.subscribe( str->{ //구독!
            System.out.println(str);
        });

        one();
    }


    private static Publisher<String> PBS = new Publisher<String>() {  //발행 클래스
        @Override
        public void subscribe(Subscriber<? super String> sbs) {  //시작의 의미, 스트림의 생성
            Subscription subscript = new Subscription() {  //행위
                @Override
                public void request(long n) {
                    sbs.onNext("abcd");
                    sbs.onNext("EFGHqq");  //onNext추가, 행위를 한번 더!
                    sbs.onComplete();
                }
                @Override
                public void cancel() {

                }
            };
            sbs.onSubscribe(subscript);  //사용자의 콜백에 대한 행위 넣기
        }
    };

    private static void one(){
        //요녀석은 Mono
        Mono.from( PBS ).map( arg-> arg.length()).subscribe( arg->{
            System.out.println("Mono : "+arg);
        });
        System.out.println(" - ");

        //요녀석은 플럭스
        Flux.from(PBS).map(arg -> arg.length()).subscribe( arg->{
            System.out.println("Flux : "+arg);
        });
    }
}