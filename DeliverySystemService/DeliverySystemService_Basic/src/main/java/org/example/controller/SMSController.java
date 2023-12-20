package org.example.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.utils.SendCode;
import org.example.web.SimpleResponse;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 刘文轩
 * @Date 2023/12/19 12:34
 */
@RestController
@Tag(name = "字典表服务")
@RequestMapping("/sms")
public class SMSController {

    @PostMapping("/sendCode")
    @ResponseBody
    public SimpleResponse sendCode (@RequestBody String jsonData){
        SimpleResponse baseResponse = new SimpleResponse();
        JSONObject data = JSONObject.parseObject(jsonData);

        String phone = data.getString("phone");
        String code = data.getString("code");
        System.out.println(phone + " " + code);
        if(code == null || phone== null){
            baseResponse.setCode(100);
            baseResponse.setMessage("空code");
        } else {
            try {
                //调用短信发送接口，三个参数，手机号，验证码，短信模板
                SendCode.sendSms(phone, code,"SMS_154950909");

            } catch (Exception e) {
                e.printStackTrace();
                baseResponse.setCode(101);
                baseResponse.setMessage("服务器问题");
            }

            baseResponse.setCode(1);
            baseResponse.setMessage("成功");

        }
        return baseResponse;

    }
}
