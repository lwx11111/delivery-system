package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.domain.order.OrderInfo;
import org.example.domain.user.RiderOrder;
import org.example.service.RiderOrderService;
import org.example.web.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 刘文轩
 * @Date 2023/12/29 19:17
 */
@RestController
@Tag(name = "服务")
@RequestMapping("/rider")
public class RiderController {
    @Autowired
    private RiderOrderService service;

    @GetMapping("/riderRegister/{id}")
    @Operation(description = "按ID查询")
    @ResponseBody
    public SimpleResponse riderRegister(@PathVariable(name = "id") String id) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.riderRegister(id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(description = "更新")
    public SimpleResponse update(@PathVariable(name = "id") String id,@RequestBody RiderOrder obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.updateByParam(obj,obj.getParams());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
