package com.djx.product.controller;

import com.djx.product.event.CreateUserEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dong jing xi
 * @date 2021/11/9 0:01
 **/
@RestController
public class TestController {


    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/test")
    public String test(){

        return "hello";
    }

    @PostMapping("/user")
    public String createUser(@RequestParam String username){
        applicationEventPublisher.publishEvent(new CreateUserEvent(username));
        return "ok";
    }

}
