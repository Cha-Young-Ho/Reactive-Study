package com.example.asyncEx.controller;

import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.processing.Completion;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * <h1>AsyncControllerV2</h1>
 *
 * <p></p>
 */
@Slf4j
@RestController
public class AsyncControllerV2 {

    @GetMapping("/async2/v1")
    public String async1(){
        return "async1";
    }

    /**
     * 다음의 코드도 문제가 있다. Servlet 쓰레드는 1개이지만 작업 쓰레드는 100개가 된다.
     * 아래에서 사용한 AsyncRestTemplate는 비동기로 처리하기위해서 백그라운드 쓰레드를 1개씩 생성한다.
     */
    @GetMapping("/async2/v2")
    public ListenableFuture<ResponseEntity<String>> requestAnotherApplication(int idx){
        AsyncRestTemplate rt = new AsyncRestTemplate();
        ListenableFuture<ResponseEntity<String>> res = rt.getForEntity("http://localhost:8081/service/?req=req",
                String.class, "hello" + idx);

        return res;
    }

    /**
     * Netty를 이용함으로 쓰레드를 100개에서 1개로 줄였다.(별도로 다른 작업을 위해서 필요한 쓰레드가 생성됨)
     */
    @GetMapping("/async2/v3")
    public ListenableFuture<ResponseEntity<String>> requestAnotherApplicationV2(){
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

        ListenableFuture<ResponseEntity<String>> res = rt.getForEntity("http://localhost:8081/service/?req=req",
                String.class, "hello");

        return res;

    }

    /**
     * 위의 내용은 api를 호출하고 결과값을 그대로 return 한다.
     * 콜백함수를 생성하여 별도의 가공을 해보자.
     */
    @GetMapping("/async2/v4")
    public DeferredResult<String> requestAnotherApplicationV3(){
        DeferredResult<String> dr = new DeferredResult<>();
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));

        ListenableFuture<ResponseEntity<String>> res = rt.getForEntity("http://localhost:8081/service/?req=req",
                String.class, "hello");
        res.addCallback(s -> {
            dr.setResult(s.getBody() + " -> after work");
        }, e->{
            dr.setErrorResult(e.getMessage());
        });
        return dr;
    }

    @GetMapping("/msa/call-back-hell")
    public DeferredResult<String> msa(){
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
        DeferredResult<String> dr = new DeferredResult<>();

        ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity("1번 서버", String.class);

        f1.addCallback(s ->{ // s = ResponseEntity<String>
            //2번 서버에게서 응답을 성공적으로 받았을 때
            ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity("2번 서버", String.class, s.getBody());
            f2.addCallback(s2 -> {
                dr.setResult(s2.getBody()); //3번 서버에서 받은 데이터를 DeferredResult에 저장
            }, e2 -> {
                // 3번 서버에서 요청 에러시
                dr.setErrorResult(e2.getMessage());
            });
        }, e ->{
            // 2번 서버에서 요청 에러시
            dr.setErrorResult(e.getMessage());
        });
        return dr;
    }

    @GetMapping("/async/call-back-hell/solution")
    public void callBackHellSolution() throws InterruptedException {
        CompletableFuture
                .runAsync(() -> log.info("runAsync"))//한번 실행할 코드를 명시하는 함수
                .thenRunAsync(() -> log.info("thenRunAsync"))
                .thenRunAsync(() -> log.info("thenThenRunAsync"));
        log.info("exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }

    @GetMapping("/async/call-back-hell/solution/chain")
    public void callBackHellSolutionChain() throws InterruptedException {
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("supplyAsync");
                    return 1;
                })
                        .thenApply(s -> {
                            log.info("thenApplyAsync");
                            return s + 1;
                        })
                                .thenApply(s2 -> {
                                    log.info("thenThenApplyAsync");
                                    return s2 + 100;
                                })
                                        .thenAccept(s3 -> log.info("result : {}", s3));
        log.info("exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }

    @GetMapping("/async/call-back-hell/solution/chain/exception")
    public void callBackHellSolutionChainException() throws InterruptedException {
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("supplyAsync");
                    return 1;
                })
                .thenApply(s -> {
                    log.info("thenApplyAsync");
                    if(true) throw new RuntimeException();
                    return s + 1;
                })
                .thenApply(s2 -> {
                    log.info("thenThenApplyAsync");
                    return s2 + 100;
                })
                .exceptionally(e -> -10)
                .thenAccept(s3 -> log.info("result : {}", s3));
        log.info("exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }

    @GetMapping("/async/call-back-hell/solution/chain2")
    public DeferredResult<String> solution(){
        AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
        DeferredResult<String> dr = new DeferredResult<>();
        toCF(rt.getForEntity("1번 요청", String.class))
                .thenCompose(s -> toCF(rt.getForEntity("2번 요청", String.class, s.getBody())))
                .thenAccept(s3 -> dr.setResult(s3.getBody()))
                .exceptionally(e -> {dr.setErrorResult(e.getMessage()); return (Void) null;}
                );

        return dr;

    }

    <T> CompletableFuture<T> toCF(ListenableFuture<T> lf){
        CompletableFuture<T> cf = new CompletableFuture<>();
        lf.addCallback(s -> cf.complete(s), e -> cf.completeExceptionally(e));
        return cf;
    }



}
