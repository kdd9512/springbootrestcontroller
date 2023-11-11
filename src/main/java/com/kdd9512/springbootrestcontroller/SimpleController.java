package com.kdd9512.springbootrestcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/hello")
    public String hello() throws InterruptedException{
        Thread.sleep(5000l);  // 5 second
        return "hello";
    }

    @GetMapping("/world")
    public String world() throws InterruptedException{
        Thread.sleep(3000l);  // 3 second
        return "world";
    }


}
