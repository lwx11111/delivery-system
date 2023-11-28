package org.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import jakarta.servlet.http.HttpServletRequest;
import org.example.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 刘文轩
 * @Date 2023/10/4 15:48
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestDao testDao;

    @PostMapping("/test")
    public void fun(@RequestBody String str){
        System.out.println(BigDecimal.valueOf((int) 1 + Math.random() * 11));
        System.out.println((BigDecimal.valueOf(1 + Math.random() * 3)));
        System.out.println(BigDecimal.valueOf(20 + Math.random() * 20));
    }

    // form-data
    @PostMapping("/testLogin")
    public void fun2(HttpServletRequest request,
                     @RequestParam("username") String username,
                     @RequestParam("password") String password){
        System.out.println("testLogin====================");
        System.out.println(username);
    }

    // raw
    @PostMapping("/testLogin1")
    public void fun3(@RequestBody String json){
        System.out.println("testLogin1====================");
        System.out.println(json);
    }

    /**
     * 更新着装校验配置
     * @return Wrapper
     */
    @PostMapping("/updateDressCheckConfig")
    public void getDressCheckConfig(){
        Integer a = testDao.testD();
        System.out.println(a);
    }
}
