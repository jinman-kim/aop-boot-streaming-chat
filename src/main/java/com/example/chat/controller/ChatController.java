package com.example.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;

@RestController
public class ChatController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ";
    private static final Random RANDOM = new Random();

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> getChatStream() {
        long startTime = System.currentTimeMillis();

        return Flux.range(1, 100)
                .delayElements(Duration.ofMillis(50)) // 각 글자 전송 사이에 지연 추가 (예: 50ms)
                .map(i -> {
                    String charStr = String.valueOf(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
                    return charStr;
                })
                .doOnComplete(() -> {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    logger.info("chat api 경과시간: {} ms", duration);
                });
    }
}
