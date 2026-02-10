package com.demo.comentoStatistic.controller;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PingController {
    @RequestMapping(value="/ping", produces = "application/json")
    @ResponseBody
    public Object healthCheck() {
        Map<String, Object> map = new HashMap<>();
        map.put("today", ZonedDateTime.now().getMonth() + ":" + ZonedDateTime.now().getDayOfMonth());
        return map;
    }
}
