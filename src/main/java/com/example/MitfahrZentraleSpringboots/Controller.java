package com.example.MitfahrZentraleSpringboots;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/hello")
    public String hello()  {
        return "Hello World!";
    }

    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "Du bist authentifziert!";
    }
}
