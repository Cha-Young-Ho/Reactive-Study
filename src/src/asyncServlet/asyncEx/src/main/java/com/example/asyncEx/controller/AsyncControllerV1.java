package com.example.asyncEx.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@RestController
public class AsyncControllerV1 {
    Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

    @GetMapping("/async/v1")
    public String asyncV1() throws InterruptedException {
        Thread.sleep(2000);
        return "hello";
    }

    @GetMapping("/async/v2")
    public Callable<String> callable(){
        return () ->{
            Thread.sleep(2000);
            return "hello callable";
        };

    }

    @GetMapping("/async/v3")
    public DeferredResult<String> deferredResult(){
        DeferredResult<String> dr = new DeferredResult<>(500000L);
        results.add(dr);
        return dr;
    }

    @GetMapping("/async/v3/deferred-count")
    public String count(){
        return String.valueOf(results.size());
    }

    @GetMapping("/async/v3/deferred-event")
    public String event(String msg){
        for (DeferredResult<String> dr : results){
            dr.setResult("Hello " + msg);
            results.remove(dr);
        }
        return "ok";
    }
}
