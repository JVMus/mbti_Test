package com.wybs.mbti.web.controller;

import net.bull.javamelody.MonitoredWithSpring;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>首页</p>
 *
 * <p>Date: 2018-05-05</p>
 *
 * @author Mumus
 */
@RestController
@MonitoredWithSpring
public class IndexController {
    @GetMapping("/")
    public String index() {
        // do nothing
        return "1";
    }
}
