package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TwitterController {
    @GetMapping("/")
    public Twit getTwit(){
        return new Twit("Hi there");
    }
}
