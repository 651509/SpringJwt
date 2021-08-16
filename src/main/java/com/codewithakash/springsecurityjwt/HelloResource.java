package com.codewithakash.springsecurityjwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

   @GetMapping({"/hello"})
    public String Hello(){
        return "hello";
    }
}
