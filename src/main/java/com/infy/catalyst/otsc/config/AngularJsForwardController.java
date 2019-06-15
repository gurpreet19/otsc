package com.infy.catalyst.otsc.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AngularJsForwardController {
    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String redirect() {
        return "forward:/";
    }
}
