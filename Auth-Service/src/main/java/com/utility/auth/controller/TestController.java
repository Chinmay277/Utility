package com.utility.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("test")
@Validated
public class TestController {

    @GetMapping("auth-test")
    public String authTest() {
        return new String("Done");
    }
    

}
