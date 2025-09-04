package com.learning.reactive.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;

@RestController
public class ReactiveDemoController {

    @GetMapping("/demo")
    public Flux<String> getItems() {
        return getItemsFromDatasource();
    }

    private Flux<String> getItemsFromDatasource() {
        return Flux.just("Pen", "Pencil", "Eraser", "Notepad"); //Publisher
    }

    public static void main(String[] args) {
        new ReactiveDemoController()
                .getItems()
                .log()
                .subscribe(System.out::println);
    }

}
