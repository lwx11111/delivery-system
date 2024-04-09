package org.example.service.impl;

import org.example.domain.shop.Category;
import org.example.dao.CategoryMapper;
import org.example.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;

import java.io.InputStream;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-06
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public void saveByParam(Category obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(Category obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Category> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Category> selectBy(Map<String, String> params) {
        QueryWrapper<Category> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Category> selectPage(Map<String, String> params) {
        Page<Category> page = PageUtils.pageHandler(params);
        QueryWrapper<Category> query = getQuery(params);
        IPage<Category> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Category> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Category> page = PageUtils.pageHandler(params);
        QueryWrapper<Category> query = getQuery(params);
        IPage<Category> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Category> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Category> page = PageUtils.pageHandler(params);
        IPage<Category> result = this.baseMapper.selpageCustomSqlByMap(page, params);
        return result;
    }

    /**
     * 下载excel模板
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @return: void
    */
    @Override
    public void downloadExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception{
        List<Category> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Category"), Category.class, data);
//        System.out.println(workbook.get);
        String fileName = String.format("Category_%d.xls", System.currentTimeMillis());
        response.setHeader("Content-Disposition", "attachment;Filename="+ fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 导入数据
     * @param file
     * @throws Exception
     */
    @Override
    public void uploadExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ImportParams params = new ImportParams();
        // bean 导入
        List<Category> dataList = new ExcelImportService().importExcelByIs(inputStream, Category.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Category> query = new QueryWrapper<>();
        List<Category> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Category"),
        Category.class, data);
        String fileName = String.format("Category_%d.xls", System.currentTimeMillis());
        response.setHeader("Content-Disposition", "attachment;Filename="+ fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    @Override
    public List<Map<String,Object>> listTreeCategory() throws Exception {
        // 所有的数据
        List<Category> all = baseMapper.selectList(null);
        // 父类数据
        List<Category> parent = all.stream().filter(category -> category.getParentId() == null).collect(Collectors.toList());
        // 子类数据
        List<Category> children = all.stream().filter(category -> category.getParentId() != null).collect(Collectors.toList());
        List<Map<String, Object>> result = Lists.newArrayList();
        for (Category category : parent) {
            Map<String,Object> temp = new HashMap<>();
            temp.put("value", category.getId());
            temp.put("label", category.getName());
            List<Map<String,Object>> childList = new ArrayList<>();
            for (Category child : children) {
                if (category.getId().equals(child.getParentId())) {
                    Map<String,Object> tempChild = new HashMap<>();
                    tempChild.put("value", child.getId());
                    tempChild.put("label", child.getName());
                    childList.add(tempChild);
                }
            }
            temp.put("children", childList);
            result.add(temp);
        }
        return result;
    }

    /**
     * 定义数据查询条件
     * @param params
     * @return
     */
    private QueryWrapper<Category> getQuery(Map<String, String> params){
        QueryWrapper<Category> query  = new QueryWrapper<>();
        if(params==null||params.size()<1) {
            return  query;
        }
        for (Map.Entry<String, String> entry:params.entrySet()){
            if(StringUtils.isBlank(entry.getValue())){
                continue;
            }
            if("id".equals(entry.getKey())){
                query.eq("id",entry.getValue());
            }
            if("name".equals(entry.getKey())){
                query.like("name",entry.getValue());
            }
            if("parentId".equals(entry.getKey())){
                query.eq("parent_id",entry.getValue());
            }
            // 分类查询
            if("isParentId".equals(entry.getKey())){
                System.out.println("entry.getValue() = " + entry.getValue());
                // 大类
                if (entry.getValue().equals("true")) {
                    query.isNull("parent_id");
                } else {
                    query.isNotNull("parent_id");
                }
            }
        }
        return query;
    }
}
