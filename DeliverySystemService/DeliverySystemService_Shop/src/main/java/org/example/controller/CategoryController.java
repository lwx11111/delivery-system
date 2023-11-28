package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.example.web.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.shop.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.ICategoryService;


import org.springframework.web.bind.annotation.RestController;

/**
 * @author lwx20
 * @since 2023-10-06
 */
@RestController
@Tag(name = "分类服务")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService service;

    @PostMapping
    @Operation(description = "创建")
    @ResponseBody
    SimpleResponse save(@RequestBody Category obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.saveByParam(obj,obj.getParams());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PutMapping("/{id}")
    @Operation(description = "更新")
    @ResponseBody
    SimpleResponse update(@PathVariable(name = "id") String id,@RequestBody Category obj){
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
    @Operation(description = "按ID删除")
    @ResponseBody
    SimpleResponse remove(@PathVariable(name = "id") String id){
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
    SimpleResponse select(@PathVariable(name = "id") String id) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getById(id));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/dels")
    @Operation(description = "按ID删除多个")
    @ResponseBody
    SimpleResponse removes(@RequestBody List<String> ids){
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
    void deleteBy(@RequestBody(required = false) Map<String, String> params) {
        service.deleteBy(params);
    }

    @PostMapping("/selby")
    @ResponseBody
    List<Category> selectBy(@RequestBody(required = false) Map<String, String> params) {
        return  service.selectBy(params);
    }

    @GetMapping("/listTreeCategory")
    @Operation(description = "分页查询")
    @ResponseBody
    public SimpleResponse listTreeCategory() {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.listTreeCategory());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/selpage")
    @Operation(description = "分页查询")
    @ResponseBody
    public SimpleResponse selectPage(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            IPage<Category> page = service.selectPage(params);
            response.setData(page);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/selpageCustomSqlByWrapper")
    @ResponseBody
    IPage<Category> selpageCustomSqlByWrapper(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByWrapper(params);
    }

    @PostMapping("/selpageCustomSqlByMap")
    @Operation(description = "分页查询-自定义sql-Map")
    @ResponseBody
    IPage<Category> selpageCustomSqlByMap(@RequestBody Map<String, String> params) {
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
    void uploadExcel(@RequestParam MultipartFile file) throws Exception {
        service.uploadExcel(file);
    }

    @PostMapping("/excel")
    @ResponseBody
    public void excel(HttpServletResponse response, HttpServletRequest request,
            @RequestBody Map<String, String> params) throws Exception {
        service.excel(response, request, params);
    }
}

