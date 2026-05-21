package com.sn.socialnetworkapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/open")
    public String open() {
        return "open";
    }

    @GetMapping("/close")
    public String close() {
        return "close";
    }

}
