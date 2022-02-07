package com.example.workshopdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

    @GetMapping("/unsecure")
    public String someMethod1(){
        return "common information";
    }

    @GetMapping("/secure")
    public String someMethod2(){
        return "secured information";
    }

    @PostMapping("/secure")
    public String someMethodPost(){
        return "post information";
    }

    @GetMapping("/secure/admin")
    public String someMethod3(){
        return "very secured information";
    }
}
