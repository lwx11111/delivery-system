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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 刘文轩
 * @Date 2023/10/4 15:48
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestDao testDao;

    public static String unicodeDecode(String string) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(string);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            string = string.replace(matcher.group(1), ch + "");
        }
        return string;
    }

    @PostMapping("/test1")
    public void fun1(@RequestBody String json){
        json = unicodeDecode(json);
        System.out.println(json);
        /**
         {
            "output_data"：[
                 "image_id": "123",
                 "ret_code": 201,
                "ret_data"：[
                     {
                     "struct_dict": {
                            "comp_onu": "123",
                            "comp_sn": "123"
                      },
                     "text_contents": []
                     },
                ],
                "ret_message": "请求成功"

            ]
            "resp_code": "000",
            "resp_message": "请求成功"
         }
         */

        // 返回结果的json
        JSONObject resJson = com.alibaba.fastjson.JSONObject.parseObject(json);
        // 000正常返回
        if ("000".equals(resJson.getString("resp_code"))) {
            // 识别结果output_data 其中"ret_code": 201识别成功
            com.alibaba.fastjson.JSONArray output_data_arr = JSON.parseArray(resJson.get("output_data").toString());
            com.alibaba.fastjson.JSONObject output_data = JSON.parseObject(output_data_arr.get(0).toString());

            if ("201".equals(output_data.getInteger("ret_code").toString())) {
                System.out.println("识别成功");
                // 获取comp_onu和comp_sn
                com.alibaba.fastjson.JSONArray ret_data_array = JSON.parseArray(output_data.get("ret_data").toString());
                for (int i = 0; i < ret_data_array.size(); i++) {
                    com.alibaba.fastjson.JSONObject struct_dictAndText_contents =
                            JSON.parseObject(ret_data_array.get(i).toString());
                    com.alibaba.fastjson.JSONObject struct_dict = JSON.parseObject(struct_dictAndText_contents.get("struct_dict").toString());
                    if (struct_dict.get("设备型号") != null){
                        System.out.println(struct_dict.get("设备型号"));

                    }
                    if(struct_dict.get("GPON SN") != null) {
                        System.out.println(struct_dict.get("GPON SN"));

                    }
                }
            } else {

            }
        } else {

        }
    }

    @PostMapping("/test")
    public void fun(){
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
