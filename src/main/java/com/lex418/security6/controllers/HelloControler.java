package com.lex418.security6.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControler {

    @GetMapping("/hello")
    public String hello(){
        return "hello12";
    }
}
