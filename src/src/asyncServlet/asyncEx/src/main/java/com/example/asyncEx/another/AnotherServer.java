package com.example.asyncEx.another;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class AnotherServer {
    @RestController
    public static class ControllerV1{

        @GetMapping("/service")
        public static String service(String req) throws InterruptedException {
            Thread.sleep(2000L);
            return "service : " + req;
        }
    }
    public static void main(String[] args) {
        System.setProperty("server.port", "8081");
        System.setProperty("server.tomcat.threads.max", "1000");
        SpringApplication.run(AnotherServer.class, args);
    }
}
