package com.funeral.upload.controller;

import com.funeral.upload.service.TestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 测试用Controller
 *
 * @author FuneralObjects
 * CreateTime 2018/11/17 9:28 PM
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TestService testService;

    @PostMapping
    public String connect(){
        testService.test();
        return "Post connect Success!";
    }
}
