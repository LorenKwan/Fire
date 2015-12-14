package com.bamboo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bob Guan on 2015/12/13.
 */
@Controller
public class GeneralController {

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public Object check(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 10010);
        map.put("message", "请先登录");
        return map;
    }

    @RequestMapping(value = "/exception", method = RequestMethod.GET)
    @ResponseBody
    public Object exception(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 10010);
        map.put("message", "接口发生异常");
        return map;
    }
}
