package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.commons.math3.analysis.function.Add;
import org.example.dto.DoubleAddressDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;
import org.example.web.SimpleResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.Address;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.IAddressService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx20
 * @since 2024-03-08
 */
@RestController
@Tag(name = "服务")
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private IAddressService service;

//    @GetMapping("/test")
//    public void test(){
//        Address a1 = new Address();
//        a1.setLatitude(BigDecimal.valueOf(43.82147) );
//        a1.setLongitude(BigDecimal.valueOf(87.581768));
//
//        Address a2 = new Address();
//        a2.setLatitude(BigDecimal.valueOf(43.856205) );
//        a2.setLongitude(BigDecimal.valueOf(87.570099));
//
//        this.getDistanceByAddress(a1, a2);
//    }

    @GetMapping("/getAddressByShopId/{shopId}")
    @ResponseBody
    @Operation(description = "根据")
    public SimpleResponse getAddressByShopId(@PathVariable String shopId){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getAddressByShopId(shopId));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/getDistanceByAddress")
    @ResponseBody
    @Operation(description = "根据经纬度获得距离信息")
    public SimpleResponse getDistanceByAddress(@RequestBody DoubleAddressDto dto){
        SimpleResponse response = new SimpleResponse();
        try {
            Address departure = dto.getDeparture();
            Address arrival = dto.getArrival();
            response.setData(service.getDistanceByAddress(departure, arrival));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping
    @ResponseBody
    @Operation(description = "创建")
    public SimpleResponse save(@RequestBody Address obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.saveByParam(obj);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(description = "更新")
    public SimpleResponse update(@PathVariable(name = "id") String id,@RequestBody Address obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.updateByParam(obj);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/{id}")
    @Operation(description = "按ID查询")
    @ResponseBody
    public SimpleResponse select(@PathVariable(name = "id") String id) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getById(id));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
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
            e.printStackTrace();
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
    public List<Address> selectBy(@RequestBody(required = false) Map<String, String> params) {
        return  service.selectBy(params);
    }

    @PostMapping("/selpage")
    @Operation(description = "分页查询")
    @ResponseBody
    public SimpleResponse selectPage(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            IPage<Address> page = service.selectPage(params);
            response.setData(page);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/selpageCustomSqlByWrapper")
    @ResponseBody
    public IPage<Address> selpageCustomSqlByWrapper(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByWrapper(params);
    }

    @PostMapping("/selpageCustomSqlByMap")
    @Operation(description = "分页查询-自定义sql-Map")
    @ResponseBody
    public IPage<Address> selpageCustomSqlByMap(@RequestBody Map<String, String> params) {
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
    public void excel(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, String> params) throws Exception {
        service.excel(response, request, params);
    }
}

