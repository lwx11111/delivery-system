package org.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.domain.Address;
import org.example.domain.shop.ShopItemVO;
import org.example.params.UpdateSumScoreParams;
import org.example.web.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.shop.Shop;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.IShopService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 店铺信息 前端控制器
 * </p>
 *
 * @author lwx20
 * @since 2023-10-16
 */
@RestController
@Tag(name = "店铺信息服务")
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private IShopService service;

    /**
     * 计算分数 非权限
     * @return
     */
    @PostMapping("/anon/calculateScore")
    @ResponseBody
    SimpleResponse calculateScore(){
        SimpleResponse response = new SimpleResponse();
        try {
            service.calculateScore();
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 更新总分
     * @return
     */
    @PostMapping("/shop/updateSumScore")
    @ResponseBody
    SimpleResponse updateSumScore(@RequestBody UpdateSumScoreParams updateSumScoreParams){
        service.updateSumScore(updateSumScoreParams);
        return new SimpleResponse.SimpleResponseBuilder().success().build();
    }

    @PostMapping("/salesVolumePlus")
    @ResponseBody
    public SimpleResponse salesVolumePlus(@RequestBody String shopId){
        SimpleResponse response = new SimpleResponse();
        try {
            service.salesVolumePlus(shopId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/getShopByOrderId/{orderId}")
    @ResponseBody
    public SimpleResponse getShopByOrderId(@PathVariable(name = "orderId") String orderId){
        try {
            Shop shop = service.getShopByOrderId(orderId);
            return new SimpleResponse.SimpleResponseBuilder().success(shop).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new SimpleResponse.SimpleResponseBuilder().failure(e.getMessage()).build();
        }
    }

    @GetMapping("/listShopItemsByShopId/{id}")
    @ResponseBody
    @Operation(description = "根据商铺号查商铺物品")
    public SimpleResponse listShopItemsByShopId(@PathVariable(name = "id") String id){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.listShopItemsByShopId(id));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/saveShopItems")
    @ResponseBody
    @Operation(description = "保存或更改商铺物品")
    public SimpleResponse saveShopItems(@RequestBody List<ShopItemVO> shopItemVOList){
        SimpleResponse response = new SimpleResponse();
        try {
            service.saveShopItems(shopItemVOList);
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping
    @ResponseBody
    @Operation(description = "创建店铺信息")
    public SimpleResponse save(@RequestBody Shop obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.saveByParam(obj,obj.getParams());
            response.setData(obj.getId());
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;

    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(description = "更新店铺信息")
    public SimpleResponse update(@PathVariable(name = "id") String id,@RequestBody Shop obj){
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
    @Operation(description = "按ID删除店铺信息")
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
    @Operation(description = "按ID查询店铺信息")
    @ResponseBody
    public SimpleResponse select(@PathVariable(name = "id") String id) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getShopById(id));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/dels")
    @ResponseBody
    @Operation(description = "按ID删除多个店铺信息")
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
    @Operation(description = "逻辑删除")
    public SimpleResponse deleteBy(@RequestBody(required = false) Map<String, String> params) {
        try {
            service.deleteBy(params);
            return new SimpleResponse.SimpleResponseBuilder().success().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new SimpleResponse.SimpleResponseBuilder().failure(e.getMessage()).build();
        }
    }

    @PostMapping("/selby")
    @ResponseBody
    public SimpleResponse selectBy(@RequestBody(required = false) Map<String, String> params) {
        List<Shop> shops = service.selectBy(params);
        return new SimpleResponse.SimpleResponseBuilder().success(shops).build();
    }

    @PostMapping("/selpage")
    @Operation(description = "分页查询店铺信息")
    @ResponseBody
    public SimpleResponse selectPage(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            String addressObject = params.get("address");
            if (addressObject != null){
                // param字段值为null 会失败
                Address address =  JSONObject.parseObject(addressObject, Address.class);
                IPage<Shop> page = service.selectPage(params, address);
                response.setData(page);
            } else {
                IPage<Shop> page = service.selectPage(params, null);
                response.setData(page);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/selpageCustomSqlByWrapper")
    @ResponseBody
    public IPage<Shop> selpageCustomSqlByWrapper(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByWrapper(params);
    }

    @PostMapping("/selpageCustomSqlByMap")
    @Operation(description = "分页查询-自定义sql-Map店铺信息")
    @ResponseBody
    public IPage<Shop> selpageCustomSqlByMap(@RequestBody Map<String, String> params) {
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

    @PostMapping("/listShopsByCategoryId")
    @Operation(description = "分页查询-根据分类ID查询店铺信息")
    @ResponseBody
    public SimpleResponse listShopsByCategoryId(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            String addressJson = params.get("address");
            Address address =  JSONObject.parseObject(addressJson, Address.class);
            response.setData(service.listShopsByCategoryId(params, address));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/listShopWithShopItemByUserId")
    @Operation(description = "分页查询-根据用户ID查询店铺和对应的物品信息-购物车、收藏")
    @ResponseBody
    public SimpleResponse listShopWithShopItemByUserId(@RequestBody Map<String, String> params) {
        // @RequestBody String id ====> ' "id" '
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.listShopWithShopItemByUserId(params));
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


}

