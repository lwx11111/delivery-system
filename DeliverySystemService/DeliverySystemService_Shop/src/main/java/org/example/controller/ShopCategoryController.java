package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.shop.ShopCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.IShopCategoryService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx20
 * @since 2023-10-06
 */
@RestController
@Tag(name = "服务")
@RequestMapping("/shopcategory")
public class ShopCategoryController {
    @Autowired
    private IShopCategoryService service;

    @PostMapping
    @Operation(description = "创建")
    void save(@RequestBody ShopCategory obj){
       service.saveByParam(obj,obj.getParams());
    }

    @PutMapping("/{id}")
    @Operation(description = "更新")
    void update(@PathVariable(name = "id") Integer id,@RequestBody ShopCategory obj){
       service.updateByParam(obj,obj.getParams());
    }

    @DeleteMapping("/{id}")
    @Operation(description = "按ID删除")
    void remove(@PathVariable(name = "id") Integer id){
          service.removeById(id);
    }

    @GetMapping("/{id}")
    @Operation(description = "按ID查询")
    @ResponseBody
    ShopCategory select(@PathVariable(name = "id") Integer id) {
       ShopCategory obj = service.getById(id);
       return obj;
    }

    @PostMapping("/dels")
    @Operation(description = "按ID删除多个")
    void removes(@RequestBody List<Integer> ids){
        service.removeByIds(ids);
    }


    @PostMapping("/delby")
    @Operation(description = "条件删除")
    void deleteBy(@RequestBody(required = false) Map<String, String> params) {
        service.deleteBy(params);
    }

    @PostMapping("/selby")
    @ResponseBody
    List<ShopCategory> selectBy(@RequestBody(required = false) Map<String, String> params) {
        return  service.selectBy(params);
    }

    @PostMapping("/selpage")
    @Operation(description = "分页查询")
    @ResponseBody
    IPage<ShopCategory> selectPage(@RequestBody Map<String, String> params) {
        return service.selectPage(params);
    }

    @PostMapping("/selpageCustomSqlByWrapper")
    @ResponseBody
    IPage<ShopCategory> selpageCustomSqlByWrapper(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByWrapper(params);
    }

    @PostMapping("/selpageCustomSqlByMap")
    @Operation(description = "分页查询-自定义sql-Map")
    @ResponseBody
    IPage<ShopCategory> selpageCustomSqlByMap(@RequestBody Map<String, String> params) {
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

