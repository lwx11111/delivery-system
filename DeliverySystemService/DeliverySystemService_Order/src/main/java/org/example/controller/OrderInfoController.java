package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.params.GetDataNearlySevenDaysParams;
import org.example.params.GetExpectedTimeParams;
import org.example.params.GetHotItemDataParams;
import org.example.rocketMQ.OrderSaveProducer;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.example.web.SimpleResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.order.OrderInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.IOrderInfoService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx20
 * @since 2023-10-31
 */
@RestController
@Tag(name = "服务")
@RequestMapping("/order")
public class OrderInfoController {

    @Autowired
    private OrderSaveProducer orderSaveProducer;

    @Autowired
    private IOrderInfoService service;

    @PostMapping("/getDataNearlySevenDays")
    @ResponseBody
    public SimpleResponse getDataNearlySevenDays(@RequestBody GetDataNearlySevenDaysParams params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getDataNearlySevenDays(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/getExpectedTime")
    @ResponseBody
    public SimpleResponse getExpectedTime(@RequestBody GetExpectedTimeParams params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getExpectedTime(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/getHotItemData")
    @ResponseBody
    public SimpleResponse getHotItemData(@RequestBody GetHotItemDataParams params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getHotItemData(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    @PostMapping("/getEarningsData")
    @ResponseBody
    public SimpleResponse getEarningsData(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {

            response.setData(service.getEarningsData(params));
            System.out.println(response.getData().toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // ======================================= 订单状态 =======================================

    @PostMapping("/orderComment")
    @ResponseBody
    public SimpleResponse orderComment(@RequestBody Map<String,String> params){
        try {
            return new SimpleResponse.SimpleResponseBuilder().success(service.orderComment(params)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new SimpleResponse.SimpleResponseBuilder().failure(e.getMessage()).build();
        }
    }

    @PostMapping("/orderPay")
    @ResponseBody
    public SimpleResponse orderPay(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.orderPay(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 商家接单
     * @param params
     * @return
     */
    @PostMapping("/orderTaking")
    @ResponseBody
    public SimpleResponse orderTaking(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.orderTaking(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 骑手配送
     * @param params
     * @return
     */
    @PostMapping("/orderDelivery")
    @ResponseBody
    public SimpleResponse orderDelivery(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.orderDelivery(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 确认收货
     * @param params
     * @return
     */
    @PostMapping("/orderReceive")
    @ResponseBody
    public SimpleResponse orderReceive(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.orderReceive(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 商家拒绝接单
     * @param params
     * @return
     */
    @PostMapping("/orderUntaking")
    @ResponseBody
    public SimpleResponse orderUntaking(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.orderUntaking(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 消费者取消订单
     * @param params
     * @return
     */
    @PostMapping("/orderCancel")
    @ResponseBody
    public SimpleResponse orderCancel(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.orderCancel(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 消费者退款
     * @param params
     * @return
     */
    @PostMapping("/orderRefund")
    @ResponseBody
    public SimpleResponse orderRefund(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.orderRefund(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    // ============================= 订单基本操作 =============================

    /**
     * 骑手订单
     * @param params
     * @return
     */
    @PostMapping("/listOrderByRiderId")
    @ResponseBody
    public SimpleResponse listOrderByRiderId(@RequestBody Map<String,String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.listOrderByRiderId(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 根据消息ID获取主键
     * @param messageId
     * @return
     */
    @PostMapping("getOrderIdByMessageId")
    @ResponseBody
    @Operation(description = "创建")
    public SimpleResponse getOrderIdByMessageId(@RequestBody String messageId){
        try {
            // 主键
            String id = service.getOrderIdByMessageId(messageId);
            System.out.println(id);
            return new SimpleResponse.SimpleResponseBuilder().success(id).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new SimpleResponse.SimpleResponseBuilder().failure(e.getMessage()).build();
        }
    }

    /**
     * 保存对象， controller -> producer -> consumer -> service
     * @param obj
     * @return
     */
    @PostMapping
    @ResponseBody
    @Operation(description = "创建")
    public SimpleResponse save(@RequestBody OrderInfo obj){
        try {
            // 消息号
            String messageId = orderSaveProducer.sendSaveOrder(obj);
            return new SimpleResponse.SimpleResponseBuilder().success(messageId).build();
        } catch (Exception e) {
            System.out.println("123");
            e.printStackTrace();
            return new SimpleResponse.SimpleResponseBuilder().failure(e.getMessage()).build();
        }
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(description = "更新")
    public SimpleResponse update(@PathVariable(name = "id") String id,@RequestBody OrderInfo obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.updateByParam(obj,obj.getParams());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(description = "按ID删除")
    public SimpleResponse remove(@PathVariable(name = "id") String id){
            SimpleResponse response = new SimpleResponse();
        try {
        service.removeById(id);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{id}")
    @Operation(description = "按ID查询")
    @ResponseBody
    public SimpleResponse select(@PathVariable(name = "id") String id) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getOrderInfoById(id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/dels")
    @ResponseBody
    @Operation(description = "按ID删除多个")
    public SimpleResponse removes(@RequestBody List<String> ids){
        SimpleResponse response = new SimpleResponse();
        try {
            service.removeByIds(ids);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    @PostMapping("/delby")
    @Operation(description = "条件删除")
    public void deleteBy(@RequestBody(required = false) Map<String, String> params) {
        service.deleteBy(params);
    }

    @PostMapping("/selby")
    @ResponseBody
    public List<OrderInfo> selectBy(@RequestBody(required = false) Map<String, String> params) {
        return  service.selectBy(params);
    }

    @PostMapping("/selpage")
    @Operation(description = "分页查询")
    @ResponseBody
    @ExceptionHandler
    public SimpleResponse selectPage(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.selectPage(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/selpageCustomSqlByWrapper")
    @ResponseBody
    public IPage<OrderInfo> selpageCustomSqlByWrapper(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByWrapper(params);
    }

    @PostMapping("/selpageCustomSqlByMap")
    @Operation(description = "分页查询-自定义sql-Map")
    @ResponseBody
    public IPage<OrderInfo> selpageCustomSqlByMap(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByMap(params);
    }

    /**
     * 下载excel模板
     * @param response
     * @param request
     * @throws Exception
     */
    @PostMapping("/downloadExcelTemplate")
    @ResponseBody
    public void downloadExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception {
        service.downloadExcelTemplate(response, request);
    }

    /**
     * excel导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/uploadExcel")
    @ResponseBody
    public void uploadExcel(@RequestParam MultipartFile file) throws Exception {
        service.uploadExcel(file);
    }

    @PostMapping("/excel")
    @ResponseBody
    public void excel(HttpServletResponse response, HttpServletRequest request,
            @RequestBody Map<String, String> params) throws Exception {
        service.excel(response, request, params);
    }

    // todo
    @PostMapping("/afterSale")
    @Operation(description = "申请售后")
    @ResponseBody
    public SimpleResponse afterSale(@RequestBody Map<String, String> params) {
//         const params = {
//                orderId: data.order.id,
//                shopId: data.shop.id,
//                reason: data.reason
//        }
        SimpleResponse response = new SimpleResponse();
        try {
            IPage<OrderInfo> page = service.selectPage(params);
            response.setData(page);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/listOrderItemById")
    @Operation(description = "查询订单的物品")
    @ResponseBody
    public SimpleResponse listOrderItemById(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.listOrderItemById(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }



}

